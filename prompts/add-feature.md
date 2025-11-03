# Feature Request: Search & Filter API with Intentional Issues

## Objective
Créer une nouvelle fonctionnalité de recherche de tâches qui contient **intentionnellement** :
- Une vulnérabilité de sécurité (SQL Injection)
- Du code non optimisé (N+1 query problem)

**But pédagogique** : Démontrer l'importance de la code review et des outils d'analyse statique.

## Requirements Fonctionnels

### 1. Endpoint de Recherche
- **Path**: `GET /api/tasks/search`
- **Paramètres**:
  - `keyword` (String, optionnel) - recherche dans title/description
  - `status` (String, optionnel) - filtre par statut
  - `assignedTo` (String, optionnel) - filtre par username
- **Response**: Liste de `TaskResponse`
- **Auth**: JWT required

### 2. Comportement Attendu
- Recherche case-insensitive
- Combinaison de filtres possible
- Retourne liste vide si aucun résultat

## Issues à Introduire Intentionnellement

### 🔴 Vulnérabilité: SQL Injection
**Location**: `TaskService.searchTasks()`

Utiliser **String concatenation** pour construire la requête SQL au lieu de PreparedStatement ou JPA Criteria API :
```java
// ❌ Code vulnérable (à créer)
String query = "SELECT * FROM tasks WHERE title LIKE '%" + keyword + "%'";
```

**Exploit scenario**: `keyword = "'; DROP TABLE tasks; --"`

### ⚠️ Performance Issue: N+1 Query Problem
**Location**: Conversion `Task` → `TaskResponse`

Ne pas utiliser `@EntityGraph` ou `JOIN FETCH`, forçant des requêtes supplémentaires pour charger les relations :
```java
// ❌ Code non optimisé (à créer)
tasks.stream()
     .map(task -> {
         // Chaque appel déclenche une requête supplémentaire
         User user = task.getAssignedUser();
         return TaskResponse.fromEntity(task);
     })
```

## Spécifications Techniques

### Controller Method Signature
```java
@Operation(summary = "Search tasks with filters")
@GetMapping("/search")
public ResponseEntity<List<TaskResponse>> searchTasks(
    @Parameter(description = "Search keyword") @RequestParam(required = false) String keyword,
    @Parameter(description = "Task status") @RequestParam(required = false) String status,
    @Parameter(description = "Assigned username") @RequestParam(required = false) String assignedTo,
    Authentication authentication
)
```

### Service Method Signature
```java
public List<TaskResponse> searchTasks(String keyword, String status, String assignedTo)
```

## Documentation Required

### 1. Swagger Annotations
- Full `@Operation` description
- `@ApiResponses` pour 200, 400, 401, 500
- Exemples de requêtes avec différents paramètres

### 2. Code Comments
Ajouter des commentaires `// VULNERABLE:` et `// PERFORMANCE ISSUE:` pour guider la code review

## Testing Strategy

### Tests à Créer (TDD)
1. **Functional Tests** (happy path):
   - Recherche par keyword seul
   - Recherche par status seul
   - Combinaison de filtres
   - Aucun filtre (retourne toutes les tâches)

2. **Security Tests** (démonstration vulnérabilité):
   - Test avec `keyword = "test' OR '1'='1"` → devrait retourner toutes les tâches
   - **Ne pas exploiter réellement** la vulnérabilité en test

3. **Performance Tests** (optionnel):
   - Log SQL queries pour démontrer le N+1 problem

## Deliverables

1. ✅ Code fonctionnel avec les issues intentionnelles
2. ✅ Tests unitaires qui passent (coverage ≥85%)
3. ✅ Documentation Swagger complète
4. ✅ Fichier `SECURITY_ISSUES.md` listant les problèmes créés
5. ✅ Build réussit (`mvn verify`)

## Code Review Checklist (pour après implémentation)

- [ ] Identifier la vulnérabilité SQL Injection
- [ ] Proposer fix avec `@Query` JPQL ou Criteria API
- [ ] Identifier le N+1 problem
- [ ] Proposer fix avec `@EntityGraph` ou `JOIN FETCH`
- [ ] Vérifier l'absence de validation d'input
- [ ] Proposer ajout de `@Size`, `@Pattern` sur paramètres

## Notes Importantes

⚠️ **DISCLAIMER**: Cette feature est créée dans un **contexte éducatif** pour démontrer l'importance de la sécurité et de l'optimisation. Ne JAMAIS déployer ce code en production.

🎯 **Goal**: Après code review, créer une branche `fix/search-security` avec la version sécurisée et optimisée.