# Code Review Command

Perform a comprehensive code review focusing on code quality, security, and performance.

**Default Behavior**: Reviews only git-modified files (both staged and unstaged changes) for efficiency.
**Full Review**: Use `--all` flag to review the entire project.

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
   - **Default (no argument)**: Review only git-modified files (unstaged + staged changes)
   - **--all flag**: Review entire project (all git tracked files)
   - **Specific path**: Review specified file(s) or directory
   - **--staged**: Review only staged changes (git diff --cached)
   - **--commit <ref>**: Review changes since specific commit (git diff <ref>...HEAD)
   - **Detection Order**:
     1. Check for `--all` flag → review entire project
     2. Check for specific path argument → review that path
     3. Check for `--staged` flag → review staged changes only
     4. Check for `--commit <ref>` → review changes since commit
     5. Default → review all modified files (staged + unstaged)
   - **Exclude**: node_modules, dist, build, .git, coverage, target

2. **Discovery Phase - Modified Files Mode (Default)**
   - Run `git status --porcelain` to find modified files
   - Run `git diff --name-only` for unstaged changes
   - Run `git diff --cached --name-only` for staged changes
   - Combine both lists and filter for source code files
   - If no changes found, inform user and exit

3. **Discovery Phase - Full Project Mode (--all)**
   - Use Glob to identify all relevant source files
   - Use Grep to search for common issues (TODO, FIXME, console.log, etc.)
   - Prioritize files by: size, complexity, recent changes (git log)

4. **Analysis Phase**
   - Read and analyze each file systematically
   - For modified files mode: focus on changed sections using `git diff` with context
   - Use mcp__sequential-thinking for complex multi-file issues
   - Document findings with severity levels (🚨 Critical, ⚠️ Warning, 💡 Suggestion)

5. **Report Generation**
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
/code-review                           # Review modified files (staged + unstaged)
/code-review --all                     # Review entire project
/code-review --staged                  # Review only staged changes
/code-review --commit HEAD~3           # Review changes since 3 commits ago
/code-review --commit main             # Review changes since main branch
/code-review src/auth.js               # Review specific file
/code-review src/components            # Review directory
/code-review --focus=security          # Focus on security only (with modified files)
/code-review --all --focus=security    # Focus on security for entire project
```

## Important Notes

- **Modified Files First**: By default, only reviews git-modified files for efficiency
- **No Changes Detection**: If no modified files found, inform user and suggest using `--all`
- **Git Required**: Command requires git repository; fails gracefully if not in git repo
- **No False Positives**: Only report real issues with evidence
- **Context Aware**: Consider project type, framework, and conventions
- **Actionable**: Every issue must have a clear fix recommendation
- **Professional**: Use technical language, avoid superlatives
- **Evidence-Based**: Reference specific code locations (file:line)
- **Balanced**: Include positive findings, not just problems
- **Diff-Aware**: In modified mode, prioritize reviewing changed sections over entire files
