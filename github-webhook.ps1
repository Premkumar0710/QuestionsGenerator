# Ensure you update these values
$ngrokPath = "C:\Users\91805\Downloads\ngrok-v3-stable-windows-amd64\ngrok.exe"  # Path to ngrok executable
$repoOwner = "Premkumar0710"  # GitHub repo owner
$repoName = "QuestionsGenerator"  # GitHub repo name
$webhookName = "github-webhook"  # The webhook name
$githubToken = "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"  # Github PAT token

# Step 1: Start ngrok to expose localhost:8080
Write-Host "Starting ngrok..."
$ngrokProcess = Start-Process -PassThru -FilePath $ngrokPath -ArgumentList "http 8080"
Start-Sleep -Seconds 5  # Wait for ngrok to start

# Step 2: Get ngrok URL from the status API
Write-Host "Fetching ngrok URL..."
$ngrokStatus = Invoke-RestMethod -Uri http://localhost:4040/api/tunnels
$publicUrl = $ngrokStatus.tunnels[0].public_url

Write-Host "Ngrok URL: $publicUrl"

# Step 3: Prepare the webhook URL (you can modify this if needed)
$webhookUrl = "$publicUrl/$webhookName/"

# Step 4: Set the GitHub Webhook URL
$githubApiUrl = "https://api.github.com/repos/$repoOwner/$repoName/hooks"

$headers = @{
    "Authorization" = "Bearer $githubToken"
    "Content-Type"  = "application/json"
}

$webhookBody = @{
    "config" = @{
        "url"           = $webhookUrl
        "content_type"  = "json"
    }
} | ConvertTo-Json

# Step 5: Update the GitHub webhook
Write-Host "Updating GitHub webhook..."
Invoke-RestMethod -Method Patch -Uri $githubApiUrl -Headers $headers -Body $webhookBody

Write-Host "GitHub webhook updated successfully!"
