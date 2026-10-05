#!/bin/bash
set -e
echo "Установка Looksmax Clicker для Arch Linux / Manjaro..."
sudo pacman -Sy --noconfirm python tk
sudo install -Dm755 ../desktop/looksmax_clicker.py /usr/share/looksmax-clicker/looksmax_clicker.py
sudo ln -sf /usr/share/looksmax-clicker/looksmax_clicker.py /usr/bin/looksmax-clicker
echo "Готово! Запуск команды: looksmax-clicker"
