# TDD Checklist for Spring Boot Task Management API

## 🎯 Purpose
This checklist ensures strict adherence to Test-Driven Development principles and 85% code coverage requirements.

---

## Pre-Development Checklist

### Before Writing ANY Code
- [ ] JDK 21 or 17 is installed and active (NOT JDK 25)
  ```bash
  java -version  # Must show 17 or 21
  ```
- [ ] Maven compiles successfully
  ```bash
  mvn clean compile
  ```
- [ ] Existing tests pass
  ```bash
  mvn test
  ```
- [ ] Baseline coverage established
  ```bash
  mvn verify
  ```

---

## TDD Cycle Checklist

### For EVERY New Feature/Endpoint/Method

#### Phase 1: RED (Write Failing Test)
- [ ] Create test file in `src/test/java/com/taskmanagement/`
- [ ] Write test method with descriptive name (e.g., `shouldCreateTaskSuccessfully`)
- [ ] Include test scenarios:
  - [ ] Happy path (success case)
  - [ ] Validation errors (invalid input)
  - [ ] Not found scenarios (invalid IDs)
  - [ ] Authentication/authorization failures
  - [ ] Edge cases (null, empty, boundary values)
- [ ] Run test → **MUST FAIL**
  ```bash
  mvn test -Dtest=YourTestClass
  ```
- [ ] Verify failure reason is correct (not compilation error)

#### Phase 2: GREEN (Make Test Pass)
- [ ] Implement minimum code in `src/main/java/com/taskmanagement/`
- [ ] Follow existing patterns (Service → Repository, Controller → Service)
- [ ] Add proper exception handling
- [ ] Include logging with @Slf4j
- [ ] Run test → **MUST PASS**
  ```bash
  mvn test -Dtest=YourTestClass
  ```

#### Phase 3: REFACTOR (Improve Code)
- [ ] Remove code duplication
- [ ] Extract common logic into methods
- [ ] Improve variable/method names
- [ ] Add/update Swagger documentation
- [ ] Run tests again → **STILL PASS**
  ```bash
  mvn test
  ```

#### Phase 4: COVERAGE (Verify Requirements)
- [ ] Run coverage check
  ```bash
  mvn verify
  ```
- [ ] **Build PASSES** (coverage ≥85%)
- [ ] Review coverage report
  ```bash
  open target/site/jacoco/index.html
  ```
- [ ] All new code shows green in coverage report
- [ ] No yellow/red branches in new code

---

## Component-Specific Checklists

### Service Layer Tests

Test File Location: `src/test/java/com/taskmanagement/service/`

Required Test Annotations:
```java
@ExtendWith(MockitoExtension.class)
class YourServiceTest {
    @Mock
    private YourRepository repository;

    @InjectMocks
    private YourService service;
}
```

Test Coverage Requirements:
- [ ] All public methods have tests
- [ ] Success scenarios tested
- [ ] Exception scenarios tested (ResourceNotFoundException, etc.)
- [ ] Repository method calls verified with ArgumentCaptor
- [ ] Entity↔DTO conversion tested
- [ ] Transaction boundary behavior tested

### Controller Layer Tests

Test File Location: `src/test/java/com/taskmanagement/controller/`

Required Test Annotations:
```java
@WebMvcTest(YourController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disable security for testing
class YourControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private YourService service;
}
```

Test Coverage Requirements:
- [ ] All HTTP endpoints tested (GET, POST, PUT, DELETE)
- [ ] Status codes verified (200, 201, 400, 404, 500)
- [ ] Request body validation tested
- [ ] Response body content verified
- [ ] Authentication/authorization tested (if applicable)
- [ ] Swagger documentation complete

### Security Components Tests

Test File Location: `src/test/java/com/taskmanagement/security/`

Required Test Scenarios:
- [ ] **JwtTokenProvider**
  - Token generation with valid authentication
  - Username extraction from valid token
  - Token validation (valid, expired, malformed)
  - Secret key configuration
- [ ] **JwtAuthenticationFilter**
  - Valid token → authentication set
  - Invalid token → authentication not set
  - Missing token → authentication not set
  - Malformed Authorization header
- [ ] **CustomUserDetailsService**
  - Load existing user → UserDetails returned
  - Load non-existent user → UsernameNotFoundException
  - Authorities correctly mapped from roles

---

## Coverage Verification Checklist

### Before Committing Code
- [ ] Run full test suite
  ```bash
  mvn clean test
  ```
- [ ] All tests pass (0 failures, 0 errors)
- [ ] Run coverage verification
  ```bash
  mvn clean verify
  ```
- [ ] Build succeeds (**BUILD SUCCESS**)
- [ ] Coverage report shows ≥85%:
  - [ ] Instruction coverage ≥85%
  - [ ] Branch coverage ≥85%
- [ ] No red/yellow code in new files
- [ ] Review coverage gaps and add tests if needed

### Coverage Report Interpretation
```
Green  = 100% covered (good)
Yellow = Partially covered (needs more tests)
Red    = Not covered (MUST add tests)
```

---

## Common Testing Patterns

### Service Test Template
```java
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Task testTask;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setAssignedUser(testUser);
    }

    @Test
    @DisplayName("Should create task successfully")
    void shouldCreateTaskSuccessfully() {
        // Arrange
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");

        when(userRepository.findByUsername("testuser"))
            .thenReturn(Optional.of(testUser));
        when(taskRepository.save(any(Task.class)))
            .thenReturn(testTask);

        // Act
        TaskResponse response = taskService.createTask(request, "testuser");

        // Assert
        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        TaskRequest request = new TaskRequest();
        when(userRepository.findByUsername("invalid"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.createTask(request, "invalid");
        });
    }
}
```

### Controller Test Template
```java
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create task via POST /api/tasks")
    void shouldCreateTask() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("New Task");

        when(taskService.createTask(any(), any()))
            .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("New Task"));
    }
}
```

---

## Quality Gates

### Minimum Standards (NON-NEGOTIABLE)
- ✅ All tests pass (`mvn test`)
- ✅ Coverage ≥85% (`mvn verify` succeeds)
- ✅ No compilation errors
- ✅ No test failures
- ✅ No skipped tests

### Recommended Standards
- 🟢 Coverage ≥90% (exceeds requirement)
- 🟢 All branches covered (100% branch coverage)
- 🟢 Integration tests for critical flows
- 🟢 Performance tests for slow operations

---

## Integration with Git Workflow

### Before Every Commit
```bash
# 1. Run tests
mvn clean test

# 2. Check coverage
mvn verify

# 3. Review changes
git diff

# 4. Stage only tested code
git add src/main/java/com/taskmanagement/service/TaskService.java
git add src/test/java/com/taskmanagement/service/TaskServiceTest.java

# 5. Commit with descriptive message
git commit -m "feat: add task creation with 90% coverage

- Implement TaskService.createTask() method
- Add comprehensive tests for success and error cases
- Coverage: 90% (exceeds 85% requirement)"
```

### Before Every Pull Request
```bash
# Full verification
mvn clean verify

# Generate fresh coverage report
open target/site/jacoco/index.html

# Ensure no test failures
grep "Tests run:" target/surefire-reports/*.txt
```

---

## Troubleshooting Test Failures

### Test Compilation Errors
- [ ] Check JDK version (must be 17 or 21, NOT 25)
- [ ] Run `mvn clean` to clear old artifacts
- [ ] Verify Lombok is configured (see CLAUDE.md)
- [ ] Check import statements (jakarta.*, not javax.*)

### Test Runtime Failures
- [ ] Read full error message carefully
- [ ] Check mock setup (when/thenReturn)
- [ ] Verify test data matches actual implementation
- [ ] Use `@DisplayName` for clarity
- [ ] Add `System.out.println` or debugger breakpoints

### Coverage Failures
- [ ] Run `open target/site/jacoco/index.html`
- [ ] Find red/yellow highlighted code
- [ ] Add tests for uncovered branches
- [ ] Test both true and false conditions
- [ ] Test exception handling paths

---

## Resources

### Commands Reference
```bash
# Quick test (single class)
mvn test -Dtest=TaskServiceTest

# Quick test (single method)
mvn test -Dtest=TaskServiceTest#shouldCreateTask

# Full build with coverage
mvn clean verify

# Skip tests (USE SPARINGLY)
mvn clean install -DskipTests

# Run with debug logging
mvn test -X

# Run specific test with debug
mvn test -Dtest=TaskServiceTest -X
```

### Key Files
- Test location: `src/test/java/com/taskmanagement/`
- Coverage config: `pom.xml` (jacoco-maven-plugin)
- Coverage report: `target/site/jacoco/index.html`
- Test reports: `target/surefire-reports/`

### Documentation
- [JUnit 5 Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [JaCoCo Maven Plugin](https://www.eclemma.org/jacoco/trunk/doc/maven.html)

---

## Success Criteria

### You Know TDD is Working When:
✅ Tests are written BEFORE implementation
✅ `mvn verify` passes consistently
✅ Coverage is ≥85% (preferably ≥90%)
✅ No yellow/red code in coverage reports
✅ All tests have meaningful assertions
✅ Tests fail for the right reasons
✅ Refactoring doesn't break tests

### You Know TDD is NOT Working When:
❌ Writing tests after implementation
❌ `mvn verify` fails due to coverage
❌ Tests pass without running code
❌ Tests have no assertions
❌ Coverage report shows red/yellow
❌ Tests are skipped or disabled
❌ "Works on my machine" syndrome

---

**Remember: TDD is not optional. It's a requirement enforced by JaCoCo. Embrace it! 🎯**
