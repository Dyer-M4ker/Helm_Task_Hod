# Docker Hub Setup for Jenkins CD

This guide walks through the steps to prepare Docker Hub for use with the CD pipeline.

## 1. Prerequisites

- Docker installed on the Jenkins agent (Docker Desktop on Windows or Docker Engine on Linux).
- A Docker Hub account with permission to push images to the target repository.
- Jenkins with the Docker Pipeline (or Docker) plugin and access to the Docker CLI on the agent.

## 2. Create a Repository on Docker Hub

1. Sign in to [hub.docker.com](https://hub.docker.com/).
2. Click **Repositories → Create repository**.
3. Choose a name, e.g. `your-username/calculator-cli`.
4. Keep the repository public or private depending on your requirements.

## 3. Generate Credentials for Jenkins

### Option A: Username and Personal Access Token

1. In Docker Hub go to **Account Settings → Security → New Access Token**.
2. Give the token a description (e.g., `jenkins-cd`), set permissions to `Read & Write`.
3. Copy the generated token (it will not be shown again).
4. In Jenkins, go to **Manage Jenkins → Credentials → (global) → Add Credentials** and choose:
   - **Kind**: Username with password
   - **Username**: your Docker Hub username
   - **Password**: the access token
   - **ID**: `dockerhub-credentials` (matching the pipeline defaults)

### Option B: Username and Password

You may use the account password, but personal access tokens are recommended.

## 4. Verify CLI Access from Jenkins Agent

On the Jenkins agent host:

```powershell
docker login --username <username> --password-stdin < <token-file>
docker pull alpine:latest
```

Ensure the Docker daemon trusts your network proxy if applicable.

## 5. Optional: Test Repository Permissions

```powershell
docker tag alpine:latest <username>/alpine-test:ci-check
docker push <username>/alpine-test:ci-check
docker rmi <username>/alpine-test:ci-check
```

Confirm the image appears in Docker Hub, then delete it.

With the credentials stored in Jenkins and the repository configured, the CD pipeline can pull images, retag them for a test environment, and run smoke tests automatically.
