# Troubleshooting Guide

## 🚨 CRITICAL: JDK 25 + Lombok Incompatibility

### Problem
The project fails to compile with the following error:
```
java.lang.NoSuchFieldException: com.sun.tools.javac.code.TypeTag :: UNKNOWN
```

### Root Cause
- Your system is using **JDK 25.0.1**
- **Lombok 1.18.34** (latest as of writing) does NOT support JDK 25
- Lombok accesses internal javac APIs that changed in JDK 25

### Solutions (Choose ONE)

#### Solution 1: Install and Use JDK 21 (RECOMMENDED)
```bash
# Install JDK 21 via Homebrew
brew install openjdk@21

# Set JAVA_HOME for current session
export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"

# Verify
java -version  # Should show Java 21

# Now compile
mvn clean install
```

#### Solution 2: Install and Use JDK 17
```bash
# Install JDK 17 via Homebrew
brew install openjdk@17

# Set JAVA_HOME
export JAVA_HOME="/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"

# Update pom.xml to use Java 17
# Change <java.version>21</java.version> to <java.version>17</java.version>
# Change <maven.compiler.source>21</maven.compiler.source> to <maven.compiler.source>17</maven.compiler.source>
# Change <maven.compiler.target>21</maven.compiler.target> to <maven.compiler.target>17</maven.compiler.target>

# Verify
java -version  # Should show Java 17

# Now compile
mvn clean install
```

#### Solution 3: Set Default JDK (Permanent Fix)
```bash
# After installing JDK 21 or 17, add to your shell profile (~/.zshrc or ~/.bash_profile):
export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

# Reload shell
source ~/.zshrc  # or source ~/.bash_profile
```

### Quick Check: Which JDK is Maven Using?
```bash
mvn --version
# Look for "Java version" line
```

---

## ⚠️ Testing Requirements Not Met

### Issue
- TDD principles require **tests written FIRST**
- JaCoCo enforces **85% minimum coverage**
- Current state: Code compiles but lacks comprehensive tests

### Required Test Coverage

#### Missing Tests:
- [ ] `AuthController` tests
  - Register endpoint (success, validation errors, duplicate user)
  - Login endpoint (success, invalid credentials, missing fields)
  - JWT token generation and validation

- [ ] `AuthService` tests
  - User registration (success, duplicate username, duplicate email)
  - User login (success, wrong password, user not found)
  - Password encoding verification

- [ ] `TaskController` tests
  - All CRUD endpoints
  - Authentication/authorization checks
  - Validation error handling

- [ ] Security Components tests
  - `JwtTokenProvider` (token generation, validation, expiration)
  - `JwtAuthenticationFilter` (valid token, invalid token, missing token)
  - `CustomUserDetailsService` (load user, user not found)

### How to Run Tests and Coverage
```bash
# Run tests only
mvn test

# Run tests with coverage report
mvn clean verify

# View coverage report (after mvn verify)
open target/site/jacoco/index.html  # macOS
# or: xdg-open target/site/jacoco/index.html  # Linux
```

### Coverage Enforcement
- Build FAILS if coverage < 85%
- Configure in `pom.xml` → jacoco-maven-plugin → check goal
- Check both INSTRUCTION and BRANCH coverage

---

## 📋 TDD Workflow (MUST FOLLOW)

### For Every New Feature:
1. **Write test FIRST** (in `src/test/java/`)
2. **Run test** → Should FAIL (Red)
3. **Implement code** (in `src/main/java/`)
4. **Run test** → Should PASS (Green)
5. **Refactor** if needed
6. **Run `mvn verify`** → Ensure 85%+ coverage

### Example: Adding New Endpoint
```bash
# 1. Create test file FIRST
# src/test/java/com/taskmanagement/controller/TaskControllerTest.java

# 2. Write test method
@Test
void shouldCreateTaskSuccessfully() {
    // Arrange, Act, Assert
}

# 3. Run test (should fail)
mvn test -Dtest=TaskControllerTest#shouldCreateTaskSuccessfully

# 4. Implement TaskController method
# src/main/java/com/taskmanagement/controller/TaskController.java

# 5. Run test again (should pass)
mvn test -Dtest=TaskControllerTest#shouldCreateTaskSuccessfully

# 6. Check coverage
mvn verify
```

---

## Common Build Issues

### Issue: "BUILD FAILURE" with No Error Details
**Solution:**
```bash
mvn clean install -X  # Run with debug logging
mvn clean install -e  # Run with exception details
```

### Issue: Tests Pass But Coverage Fails
**Solution:**
- Coverage < 85% threshold
- Add more test cases for uncovered branches
- Check `target/site/jacoco/index.html` for red/yellow highlighted code

### Issue: Port 8080 Already in Use
**Solution:**
```bash
# Find process using port 8080
lsof -i :8080

# Kill process (replace PID with actual process ID)
kill -9 PID

# OR run on different port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Issue: H2 Console Not Working
**Solution:**
1. Check `application.properties`: `spring.h2.console.enabled=true`
2. Access: `http://localhost:8080/h2-console`
3. JDBC URL: `jdbc:h2:mem:taskdb`
4. Username: `sa`
5. Password: (leave empty)

---

## IDE Setup for Lombok

### IntelliJ IDEA
1. Install Lombok plugin: File → Settings → Plugins → Search "Lombok"
2. Enable annotation processing: File → Settings → Build → Compiler → Annotation Processors → Enable
3. Restart IDE

### VS Code
1. Install "Lombok Annotations Support" extension
2. Reload window

### Eclipse
1. Download lombok.jar from https://projectlombok.org/download
2. Run: `java -jar lombok.jar`
3. Select Eclipse installation
4. Click "Install/Update"

---

## Quick Reference

### Essential Maven Commands
```bash
mvn clean                 # Clean build artifacts
mvn compile               # Compile source code
mvn test                  # Run tests
mvn verify                # Run tests + coverage check
mvn clean install         # Full build (clean + compile + test + package)
mvn spring-boot:run       # Run application
```

### Check Project Health
```bash
# 1. Check Java version
java -version
mvn --version

# 2. Verify dependencies
mvn dependency:tree

# 3. Run tests
mvn test

# 4. Check coverage
mvn verify
open target/site/jacoco/index.html
```

---

## Next Steps

### After Fixing JDK Issue:
1. ✅ Ensure project compiles: `mvn clean compile`
2. ⬜ Write missing tests (see "Missing Tests" section)
3. ⬜ Run tests: `mvn test`
4. ⬜ Verify coverage: `mvn verify` (must be ≥85%)
5. ⬜ Update CLAUDE.md with TDD reminders
6. ⬜ Create TDD_CHECKLIST.md for future development

### Test Writing Priority:
1. **HIGH**: AuthService (core business logic)
2. **HIGH**: TaskService (already has some tests, complete coverage)
3. **MEDIUM**: AuthController (API layer)
4. **MEDIUM**: TaskController (API layer)
5. **LOW**: Security components (infrastructure)

---

## Need Help?

### Check Logs
```bash
# Application logs
tail -f logs/application.log

# Maven logs
mvn clean install > build.log 2>&1
cat build.log
```

### Verify Configuration
```bash
# Check Spring Boot properties
cat src/main/resources/application.properties

# Check pom.xml dependencies
mvn dependency:list
```

### Test Individual Components
```bash
# Test specific class
mvn test -Dtest=TaskServiceTest

# Test specific method
mvn test -Dtest=TaskServiceTest#shouldCreateTaskSuccessfully

# Run with debug
mvn test -Dtest=TaskServiceTest -X
```
