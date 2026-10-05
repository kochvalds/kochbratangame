Write-Host "Запуск Looksmax Clicker v1.8.0..." -ForegroundColor Cyan
if (!(Get-Command python -ErrorAction SilentlyContinue)) {
    Write-Warning "Python не установлен. Скачайте с https://python.org"
    Read-Host "Нажмите Enter для выхода..."
    exit
}
python looksmax_clicker.py
