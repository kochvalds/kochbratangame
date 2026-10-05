#!/bin/bash
set -e
echo "Установка Looksmax Clicker для Debian / Ubuntu / Linux Mint..."
sudo apt update
sudo apt install -y python3 python3-tk
sudo dpkg -i looksmax-clicker_1.8.0_all.deb || sudo apt install -f -y
echo "Готово! Запуск команды: looksmax-clicker"
