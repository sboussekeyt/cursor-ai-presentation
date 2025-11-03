# Code Review Command

Perform a comprehensive code review focusing on code quality, security, and performance.

## Objectives

You are conducting a thorough code review with the following focus areas:

### 1. Code Quality (🎯)

- **Readability**: Clear naming, proper formatting, self-documenting code
- **Maintainability**: DRY principle, SOLID principles, low complexity
- **Best Practices**: Follow language/framework conventions and patterns
- **Code Smells**: Detect duplications, long functions, god objects
- **Testing**: Test coverage, test quality, missing test cases

### 2. Security (🛡️)

- **Vulnerabilities**: SQL injection, XSS, CSRF, authentication issues
- **Sensitive Data**: Hardcoded secrets, exposed credentials, data leaks
- **Dependencies**: Outdated packages with known vulnerabilities
- **Access Control**: Proper authorization, input validation, output encoding
- **Best Practices**: OWASP Top 10 compliance, security headers

### 3. Performance (⚡)

- **Algorithm Complexity**: Identify O(n²) or worse algorithms
- **Resource Usage**: Memory leaks, excessive allocations, inefficient loops
- **Database**: N+1 queries, missing indexes, inefficient queries
- **Caching**: Missing caching opportunities, stale cache issues
- **Bottlenecks**: Identify slow operations and optimization opportunities

## Execution Pattern

1. **Scope Analysis**
   - If argument provided: Review specified file(s) or directory
   - If no argument: Review entire project (git tracked files)
   - Exclude: node_modules, dist, build, .git, coverage

2. **Discovery Phase**
   - Use Glob to identify all relevant source files
   - Use Grep to search for common issues (TODO, FIXME, console.log, etc.)
   - Prioritize files by: size, complexity, recent changes (git log)

3. **Analysis Phase**
   - Read and analyze each file systematically
   - Use mcp__sequential-thinking for complex multi-file issues
   - Document findings with severity levels (🚨 Critical, ⚠️ Warning, 💡 Suggestion)

4. **Report Generation**
   - Create structured report in `claudedocs/code-review-[timestamp].md`
   - Organize by severity and category
   - Include file paths with line numbers (file:line format)
   - Provide actionable recommendations with code examples

## Output Format

```markdown
# Code Review Report - [Date]

## Executive Summary
- Files Reviewed: X
- Issues Found: Y (Z Critical, W Warnings, V Suggestions)
- Overall Score: [1-10]

## Critical Issues (🚨)
### [Category] Issue Title
- **Location**: `file.js:123`
- **Problem**: Description
- **Impact**: What could go wrong
- **Fix**: Recommended solution with code example

## Warnings (⚠️)
[Same format as Critical]

## Suggestions (💡)
[Same format as Critical]

## Positive Findings (✅)
- Well-implemented patterns
- Good practices observed

## Recommendations
1. Priority actions
2. Long-term improvements
3. Additional tooling suggestions
```

## Usage Examples

```bash
/code-review                           # Review entire project
/code-review src/auth.js              # Review specific file
/code-review src/components           # Review directory
/code-review --focus=security         # Focus on security only
```

## Important Notes

- **No False Positives**: Only report real issues with evidence
- **Context Aware**: Consider project type, framework, and conventions
- **Actionable**: Every issue must have a clear fix recommendation
- **Professional**: Use technical language, avoid superlatives
- **Evidence-Based**: Reference specific code locations (file:line)
- **Balanced**: Include positive findings, not just problems
