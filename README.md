## Running the project

1. Start the infrastructure:

   ```bash
   docker compose up
   ```

2. Configure Keycloak:

    1. Open Keycloak: http://localhost:8081
        - Username: `admin`
        - Password: `admin`
    2. Select the `issue-tracker` realm.
    3. Navigate to **Clients** → **issue-tracker-api** → **Credentials**.
    4. Click **Regenerate** next to **Client Secret**.
    5. Copy the generated client secret to `application-secrets.yml`:

       ```yaml
       keycloak:
         client-secret: <generated-secret>
       ```

## Zajímavé části projektu

- `AuditService.kt`
- `CreateTaskUC.kt`, `UpdateTaskUC`, `BrowseTasksUC`
- k tomu související `...Query.kt` a `...Command.kt` soubory
