## Gérer le cycle de vie d’un plugin avec Spring Statemachine

Ce projet démontre comment utiliser **Spring Statemachine** pour gérer le **cycle de vie d’un plugin dynamique** dans une application modulaire.  
Au lieu de coder des règles métier avec des `if (state == ACTIVE)`, nous déclarons **explicitement** les états, événements et transitions, ce qui rend le code **sûr, testable et lisible**.

---

## Fonctionnalités

-  **Gestion complète du cycle de vie d’un plugin** :  
  `INSTALLED` → `ACTIVE` → `INACTIVE` → `UNINSTALLED` + `FAILED`
-  **Transitions validées par Spring Statemachine** :  
  Impossible d’activer un plugin désinstallé, ou de désactiver un plugin non activé.
-  **Actions exécutées lors des transitions** (logging, intégration future avec des services).
-  **Injection par constructeur** — pas de `@Autowired`, seulement des bonnes pratiques.
-  **API REST** pour tester les transitions via `curl`.
-  **Logs structurés** avec SLF4J/Logback — pas de `System.out.println`.
-  **Tests avec cURL** inclus — prêts à être exécutés en 1 clic.

---

## Technologies utilisées

| Technologie | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.5.7 |
| Spring Statemachine | 4.0.1 |


## Lancez l'application

./gradlew bootRun
