# Harbor Installation Walkthrough

This guide summarizes the steps required to install Harbor on a Windows host using Docker Desktop and Docker Compose. Adjust paths and values for your environment as needed.

## 1. Prerequisites

- **Hardware**: At least 4 CPU cores, 8 GB RAM, 40 GB free disk space.
- **Software**:
  - Windows 10/11 with Hyper-V or WSL2 enabled.
  - [Docker Desktop](https://www.docker.com/products/docker-desktop/) with **WSL2 backend** (or native Hyper-V). Ensure Docker Compose V2 is enabled.
  - OpenSSL (used to generate TLS certificates). You can install it via [Chocolatey](https://community.chocolatey.org/packages/openssl) or WSL.
- **Network**: DNS or hosts entries for Harbor domain (e.g., `harbor.local`).

> Tip: WSL2 installation generally provides a smoother experience with Harbor than Hyper-V.

## 2. Download Harbor

```powershell
wget https://github.com/goharbor/harbor/releases/download/v2.11.3/harbor-offline-installer-v2.11.3.tgz -OutFile harbor.tgz
tar -xzf harbor.tgz
cd harbor
```

> Use the *offline* installer when the host has limited external network access. Replace the version with the latest LTS release as needed.

## 3. Generate TLS Certificates (Optional but Recommended)

Generate a private certificate authority (CA) and server certificates for your Harbor domain:

```bash
openssl genrsa -out ca.key 4096
openssl req -x509 -new -nodes -key ca.key -days 3650 -out ca.crt -subj "/CN=harbor-ca"

openssl genrsa -out harbor.key 4096
openssl req -new -key harbor.key -out harbor.csr -subj "/CN=harbor.local"
openssl x509 -req -in harbor.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out harbor.crt -days 3650
```

Copy `harbor.crt` and `harbor.key` into a directory, then reference them in `harbor.yml` (next step). Install `ca.crt` into the Docker Desktop and Windows trust stores so Docker clients trust the registry.

## 4. Configure Harbor

Create a copy of the default configuration:

```powershell
Copy-Item harbor.yml.tmpl harbor.yml
```

Edit `harbor.yml`:

- Set `hostname: harbor.local` (or your chosen domain).
- Configure `https:` section with paths to your certificates.
- Optionally change the administrator password (`harbor_admin_password`).
- Choose storage backend (filesystem is default).

## 5. Install Harbor

Run the preparation and installation scripts:

```powershell
.\prepare.ps1    # Use prepare.sh if running in WSL
.\install.ps1
```

For PowerShell 7, call `.\prepare.ps1 -WithNotary -WithTrivy` as needed to enable specific services.

Verify containers are running:

```powershell
docker compose ps
```

Browse to `https://harbor.local` and log in with `admin` / configured password.

## 6. Create a Project and Configure Robot Account

1. Create a new project `calculator` (public/private as desired).
2. Navigate to **Administration → Users → Robot Accounts** (or Project → Robot Accounts) and create a robot user for Jenkins (e.g., `robot$jenkins`).
3. Copy the generated username and token; store them in Jenkins credentials (username/password) for pipeline authentication.

## 7. Connectivity From Jenkins

On the Jenkins agent:

```powershell
docker login harbor.local -u 'robot$jenkins' -p '<robot-token>'
```

If using self-signed certificates, copy `ca.crt` to `/etc/docker/certs.d/harbor.local/ca.crt` (Linux) or `%PROGRAMDATA%\docker\certs.d\harbor.local\ca.crt` (Windows) and restart Docker Desktop.

## 8. Maintenance

- Regularly apply Harbor updates: stop the stack (`docker compose down`), replace binaries, run `prepare`, and `install` again.
- Use Harbor's built-in vulnerability scanner (Trivy) to audit images.
- Configure retention policies and replication as needed.

With Harbor running, Jenkins pipelines can push, pull, and promote images using the Harbor REST API or Docker CLI.
