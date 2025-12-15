# Approche de Refactoring

## Décision méthodologique

Après avoir commencé l'ajout de fonctionnalités, j'ai constaté que plusieurs améliorations de refactoring prévues n'étaient pas encore complétées. J'ai donc choisi de finaliser le refactoring avant de poursuivre, garantissant une base de code solide et respectant les principes SOLID avant l'ajout de nouvelles fonctionnalités.

## Améliorations complétées

- Implémentation de FreeMarker pour le formatage d'affichage
- Création d'interfaces pour les managers (IHealthManager, IExperienceManager, etc.)
- Extraction de la logique d'affichage dans PlayerFormatter
- Nettoyage du code (imports non utilisés, variables inutiles)
- Organisation des interfaces dans le dossier `interfaces/`