# Ensure you update these values
$ngrokPath = "C:\Users\91805\Downloads\ngrok-v3-stable-windows-amd64\ngrok.exe"  # Path to ngrok executable
$repoOwner = "Premkumar0710"  # GitHub repo owner
$repoName = "QuestionsGenerator"  # GitHub repo name
$webhookName = "github-webhook"  # The webhook name
$githubToken = "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"  # GitHub Personal Access Token (PAT)
$jenkinsUrl = "http://localhost:8080"  # The Jenkins URL (before dynamic update)
$jenkinsApiToken = "1101ec8ba9b2d31ada5cdb049cc3185636"  # Jenkins API token
$jenkinsUser = "admin"  # Jenkins username

# Step 1: Start ngrok to expose localhost:8080 (Jenkins)
Write-Host "Starting ngrok..."
$ngrokProcess = Start-Process -PassThru -FilePath $ngrokPath -ArgumentList "http 8080"
Start-Sleep -Seconds 5  # Wait for ngrok to start

# Step 2: Get ngrok public URL from its API
Write-Host "Fetching ngrok URL..."
$ngrokStatus = Invoke-RestMethod -Uri http://localhost:4040/api/tunnels
$publicUrl = $ngrokStatus.tunnels[0].public_url
$webhookUrl = "$publicUrl/$webhookName/"
Write-Host "Ngrok Public URL: $webhookUrl"

# Step 3: Fetch existing GitHub webhooks
$headers = @{
    "Authorization" = "Bearer $githubToken"
    "User-Agent"    = "ngrok-updater"
    "Accept"        = "application/vnd.github.v3+json"
}
$hooksApiUrl = "https://api.github.com/repos/$repoOwner/$repoName/hooks"
$hooks = Invoke-RestMethod -Uri $hooksApiUrl -Headers $headers

# Step 4: Find the matching webhook by webhookName
$targetHook = $hooks | Where-Object { $_.config.url -like "*$webhookName*" }
if (-not $targetHook) {
    Write-Host "❌ Webhook not found. Please ensure it exists."
    exit 1
}
$webhookId = $targetHook.id

# Step 5: Update GitHub webhook with new ngrok URL
$updateUrl = "https://api.github.com/repos/$repoOwner/$repoName/hooks/$webhookId"
$body = @{
    config = @{
        url = $webhookUrl
        content_type = "json"
    }
} | ConvertTo-Json -Depth 3

Write-Host "Updating GitHub webhook..."
Invoke-RestMethod -Method Patch -Uri $updateUrl -Headers $headers -Body $body

Write-Host "✅ GitHub webhook updated successfully to: $webhookUrl"

# Step 6: Update Jenkins URL dynamically

# Fetch Jenkins CSRF token (for security)
$crumbResponse = Invoke-RestMethod -Uri "$jenkinsUrl/crumbIssuer/api/json" -Method Get -Headers @{
    "Authorization" = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("$jenkinsUser:$jenkinsApiToken"))
}
$jenkinsCrumb = $crumbResponse.crumb

# Update Jenkins URL with the new ngrok URL
$updateJenkinsUrlApi = "$jenkinsUrl/configure"
$updateBody = @{
    "jenkinsUrl" = $publicUrl
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("$jenkinsUser:$jenkinsApiToken"))
    "Jenkins-Crumb" = $jenkinsCrumb
    "Content-Type" = "application/json"
}

# Update the Jenkins configuration with the new ngrok URL
Write-Host "Updating Jenkins URL in Jenkins config..."
Invoke-RestMethod -Uri $updateJenkinsUrlApi -Method Post -Headers $headers -Body $updateBody

Write-Host "✅ Jenkins URL updated to: $publicUrl"
