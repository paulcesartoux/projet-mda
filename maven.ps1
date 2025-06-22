# Autoriser l'exécution de scripts
Set-ExecutionPolicy Bypass -Scope Process -Force

# Installation de Chocolatey (si non présent)
if (!(Get-Command choco.exe -ErrorAction SilentlyContinue)) {
    Write-Host "Installation de Chocolatey..."
    [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
} else {
    Write-Host "Chocolatey est déjà installé."
}

# Mettre à jour le PATH temporairement pour cette session
$chocoPath = [System.Environment]::GetEnvironmentVariable('ChocolateyInstall','Machine')
if ($chocoPath) {
    $env:Path += ";$chocoPath\bin"
}

# Désinstaller Java et Maven (installés par choco)
Write-Host "`nSuppression des anciennes versions de Java et Maven..."
choco uninstall -y openjdk
choco uninstall -y maven

# Installer Java et Maven
Write-Host "`nInstallation de Java (OpenJDK) et Apache Maven..."
choco install -y openjdk
choco install -y maven

# Vérification
Write-Host "`nVérification des installations :"
java -version
mvn -version

Write-Host "`nInstallation terminée avec succès."
