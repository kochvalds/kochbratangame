@echo off
title Looksmax Clicker v1.8.0 - Brawl Stars Edition
echo Запуск Looksmax Clicker...
where python >nul 2>nul
if %errorlevel% neq 0 (
    echo Python не найден! Установите Python 3 с python.org
    pause
    exit /b
)
python looksmax_clicker.py
pause
