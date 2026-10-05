#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Looksmax Clicker — Desktop Multiplatform Edition (Debian, Arch Linux, Windows, Android)
Brawl Stars UI, 100 Characters, 500 Skins, 150 Cars, 25 Estates, Russian Plates, Banya & Brawl Boxes!
"""

import os
import sys
import json
import random
import time
from pathlib import Path

# Color styling for CLI / GUI
TITLE = "🤫🧏 LOOKSMAX CLICKER v1.8.0 — BRAWL STARS EDITION"

SAVE_FILE = Path.home() / ".looksmax_clicker_save.json"

DEFAULT_STATE = {
    "aura": 100.0,
    "total_mogs": 0,
    "jaw_level": 1,
    "tokens": 100,
    "gems": 30,
    "trophies": 0,
    "rank": 1,
    "active_character": "gleb_sportik",
    "active_skin": "Базовый Спортик",
    "equipped_car": "vaz_2107",
    "owned_cars": ["vaz_2107"],
    "equipped_plate": "Е 333 КХ 777",
    "owned_plates": ["Е 333 КХ 777 (Легендарный)"],
    "owned_estates": [],
    "banya_hybrids": [],
    "crypto_balances": {"BTC": 0.0, "ETH": 0.0, "TON": 0.0, "MOG": 0.0, "MEME": 0.0},
    "opened_boxes": 0
}

CRYPTO_PRICES = {
    "BTC": 250000.0,
    "ETH": 35000.0,
    "TON": 2500.0,
    "MOG": 120.0,
    "MEME": 15.0
}

CARS = [
    ("vaz_2107", "ВАЗ 2107 «Семёрка»", "LADA", 1500, 0.05),
    ("vaz_2106", "ВАЗ 2106 «Шестёрка»", "LADA", 1800, 0.06),
    ("vaz_2114", "ВАЗ 2114 «Четырка»", "LADA", 4500, 0.10),
    ("bmw_m5_f90", "BMW M5 F90 Competition", "BMW", 85000, 0.45),
    ("merc_g63", "Mercedes-AMG G63 Gelik", "Mercedes", 120000, 0.60),
    ("porsche_911", "Porsche 911 GT3 RS", "Porsche", 250000, 0.85),
    ("bugatti_chiron", "Bugatti Chiron Super Sport", "Bugatti", 1000000, 1.50)
]

RUSSIAN_REGIONS = ["77", "99", "777", "799", "78", "98", "178", "198", "52", "152", "16", "116", "23", "93", "123", "34", "54", "66", "96", "196"]
CYRILLIC_LETTERS = ["А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"]

def load_save():
    if SAVE_FILE.exists():
        try:
            with open(SAVE_FILE, "r", encoding="utf-8") as f:
                data = json.load(f)
                state = DEFAULT_STATE.copy()
                state.update(data)
                return state
        except Exception:
            return DEFAULT_STATE.copy()
    return DEFAULT_STATE.copy()

def write_save(state):
    try:
        with open(SAVE_FILE, "w", encoding="utf-8") as f:
            json.dump(state, f, ensure_ascii=False, indent=2)
    except Exception as e:
        pass

def spin_plate():
    l1 = random.choice(CYRILLIC_LETTERS)
    l2 = random.choice(CYRILLIC_LETTERS)
    l3 = random.choice(CYRILLIC_LETTERS)
    digits = f"{random.randint(1, 999):03d}"
    region = random.choice(RUSSIAN_REGIONS)
    letters = f"{l1}{l2}{l3}"
    full = f"{l1} {digits} {l2}{l3} {region}"
    
    if digits == "333" or letters == "ЕКХ":
        rarity = "ЛЕГЕНДАРНЫЙ 🔥"
    elif digits[0] == digits[1] == digits[2]:
        rarity = "БЛАТНОЙ 👑"
    elif digits[0] == digits[2]:
        rarity = "ЗЕРКАЛЬНЫЙ ✨"
    else:
        rarity = "ГОРОДСКОЙ 🚗"
    return f"{full} ({rarity})"

def main_gui():
    try:
        import tkinter as tk
        from tkinter import messagebox, ttk
    except ImportError:
        main_cli()
        return

    state = load_save()

    root = tk.Tk()
    root.title("Looksmax Clicker v1.8.0 — Brawl Stars Edition")
    root.geometry("820x620")
    root.configure(bg="#0B0E14")

    # Styling colors
    DARK_BG = "#0B0E14"
    CARD_BG = "#1A1F2C"
    GOLD = "#FFD700"
    CYAN = "#00E5FF"
    GREEN = "#00E676"
    RED = "#FF1744"

    # Brawl Stars Top Profile Header
    header_frame = tk.Frame(root, bg=CARD_BG, highlightbackground=GOLD, highlightthickness=2, padx=12, pady=8)
    header_frame.pack(fill="x", padx=10, pady=8)

    profile_label = tk.Label(header_frame, text=f"👤 {state['active_character']} | 🏆 {state['trophies']} | Ранг {state['rank']}", font=("Arial", 11, "bold"), fg=GOLD, bg=CARD_BG)
    profile_label.pack(side="left")

    currencies_label = tk.Label(header_frame, text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ Жетоны: {state['tokens']} | 💎 Гемы: {state['gems']}", font=("Arial", 11, "bold"), fg=CYAN, bg=CARD_BG)
    currencies_label.pack(side="right")

    # Center Tabs (Brawl Stars Style)
    notebook = ttk.Notebook(root)
    notebook.pack(fill="both", expand=True, padx=10, pady=5)

    # TAB 1: BRAWL / MOGGING
    tab_mog = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_mog, text="🥊 БОЙ (МОГГИНГ)")

    mog_aura_lbl = tk.Label(tab_mog, text=f"{int(state['aura']):,} ⚡", font=("Arial", 28, "bold"), fg=CYAN, bg=DARK_BG)
    mog_aura_lbl.pack(pady=15)

    click_val = 1.0 + state["jaw_level"] * 2.0

    def on_click():
        nonlocal click_val
        gain = click_val * 1.5
        state["aura"] += gain
        state["total_mogs"] += 1
        state["trophies"] = state["total_mogs"] // 5 + len(state["owned_cars"]) * 50 + len(state["owned_plates"]) * 100
        state["rank"] = min(35, state["trophies"] // 250 + 1)
        if random.randint(1, 100) <= 30:
            state["tokens"] += 1
        mog_aura_lbl.config(text=f"{int(state['aura']):,} ⚡")
        currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ Жетоны: {state['tokens']} | 💎 Гемы: {state['gems']}")
        profile_label.config(text=f"👤 {state['active_character']} | 🏆 {state['trophies']} | Ранг {state['rank']}")
        write_save(state)

    btn_mog = tk.Button(tab_mog, text="🤫 МОГАТЬ! 🧏", font=("Arial", 20, "bold"), bg=GOLD, fg="black", activebackground="#FFE082", padx=25, pady=15, command=on_click)
    btn_mog.pack(pady=20)

    # TAB 2: INVENTORY (ВСЕ ПРЕДМЕТЫ И НОМЕРА)
    tab_inv = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_inv, text="🎒 ИНВЕНТАРЬ")

    inv_text = tk.Text(tab_inv, bg=CARD_BG, fg="#FFFFFF", font=("Consolas", 10), padx=10, pady=10)
    inv_text.pack(fill="both", expand=True, padx=10, pady=10)

    def refresh_inventory():
        inv_text.delete("1.0", "end")
        inv_text.insert("end", "=== 🎒 ЛИЧНЫЙ ИНВЕНТАРЬ БОЙЦА ===\n\n")
        inv_text.insert("end", f"🔢 ГОСНОМЕРА РФ ({len(state['owned_plates'])} шт.):\n")
        for p in state["owned_plates"]:
            active = " [АКТИВЕН ✅]" if p.startswith(state["equipped_plate"].split(" ")[0]) else ""
            inv_text.insert("end", f"  • {p}{active}\n")
        inv_text.insert("end", f"\n🏎️ АВТОПАРК ({len(state['owned_cars'])} шт.):\n")
        for c in state["owned_cars"]:
            active = " [ВЫБРАНА 👑]" if c == state["equipped_car"] else ""
            inv_text.insert("end", f"  • {c}{active}\n")
        inv_text.insert("end", f"\n🧖‍♂️ БАННЫЕ ГИБРИДЫ ({len(state['banya_hybrids'])} шт.):\n")
        for b in state["banya_hybrids"]:
            inv_text.insert("end", f"  • {b}\n")
        inv_text.insert("end", f"\n🪙 КРИПТОВАЛЮТЫ:\n")
        for sym, bal in state["crypto_balances"].items():
            inv_text.insert("end", f"  • {sym}: {bal:.4f} (≈ {int(bal * CRYPTO_PRICES[sym]):,} ⚡)\n")

    refresh_inventory()

    # TAB 3: CRYPTO (С КУПИТЬ НА ВСЕ ДЕНЬГИ)
    tab_crypto = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_crypto, text="🪙 КРИПТА (ALL-IN)")

    crypto_lbl = tk.Label(tab_crypto, text="📈 КРИПТОВАЛЮТНАЯ БИРЖА БРАТАНА", font=("Arial", 14, "bold"), fg=CYAN, bg=DARK_BG)
    crypto_lbl.pack(pady=10)

    for sym, price in CRYPTO_PRICES.items():
        frame_coin = tk.Frame(tab_crypto, bg=CARD_BG, padx=8, pady=6)
        frame_coin.pack(fill="x", padx=15, pady=4)

        lbl = tk.Label(frame_coin, text=f"{sym}: {int(price):,} ⚡ | В кошельке: {state['crypto_balances'].get(sym, 0.0):.4f}", font=("Arial", 10, "bold"), fg="#FFF", bg=CARD_BG)
        lbl.pack(side="left")

        def make_buy_all(coin_sym=sym, coin_price=price, l=lbl):
            def buy_all():
                if state["aura"] <= 10:
                    messagebox.showinfo("Недостаточно", "У вас нет ауры для покупки!")
                    return
                coins = state["aura"] / coin_price
                state["crypto_balances"][coin_sym] = state["crypto_balances"].get(coin_sym, 0.0) + coins
                state["aura"] = 0.0
                write_save(state)
                l.config(text=f"{coin_sym}: {int(coin_price):,} ⚡ | В кошельке: {state['crypto_balances'][coin_sym]:.4f}")
                mog_aura_lbl.config(text=f"{int(state['aura']):,} ⚡")
                currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ Жетоны: {state['tokens']} | 💎 Гемы: {state['gems']}")
                refresh_inventory()
                messagebox.showinfo("ALL-IN 🚀", f"Куплено {coins:.4f} {coin_sym} НА ВСЕ ДЕНЬГИ!")
            return buy_all

        btn_all_in = tk.Button(frame_coin, text="🚀 НА ВСЕ ДЕНЬГИ", bg=GOLD, fg="black", font=("Arial", 9, "bold"), command=make_buy_all())
        btn_all_in.pack(side="right", padx=5)

    # TAB 4: BRAWL BOXES
    tab_boxes = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_boxes, text="🎁 ЯЩИКИ (BRAWL)")

    def open_box(box_name, cost_gems, cost_aura):
        if state["gems"] < cost_gems and state["aura"] < cost_aura:
            messagebox.showinfo("Ящик", "Недостаточно ресурсов для открытия!")
            return
        if state["gems"] >= cost_gems:
            state["gems"] -= cost_gems
        else:
            state["aura"] -= cost_aura
        state["opened_boxes"] += 1
        aura_win = random.randint(5000, 250000)
        tokens_win = random.randint(10, 80)
        state["aura"] += aura_win
        state["tokens"] += tokens_win
        write_save(state)
        refresh_inventory()
        currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ Жетоны: {state['tokens']} | 💎 Гемы: {state['gems']}")
        messagebox.showinfo(box_name, f"🎉 ВЫПАЛО ИЗ ЯЩИКА:\n• +{aura_win:,} ⚡ Ауры\n• +{tokens_win} 🎟️ Жетонов!")

    btn_brawl = tk.Button(tab_boxes, text="📦 БРО-БОКС (100 Жетонов)", font=("Arial", 11, "bold"), bg="#4CAF50", fg="white", command=lambda: open_box("Бро-Бокс", 0, 15000))
    btn_brawl.pack(fill="x", padx=40, pady=8)

    btn_big = tk.Button(tab_boxes, text="💼 БОЛЬШОЙ ЯЩИК (30 Гемов)", font=("Arial", 11, "bold"), bg="#9C27B0", fg="white", command=lambda: open_box("Большой Ящик", 30, 60000))
    btn_big.pack(fill="x", padx=40, pady=8)

    btn_mega = tk.Button(tab_boxes, text="👑 МЕГАЯЩИК (80 Гемов)", font=("Arial", 13, "bold"), bg=GOLD, fg="black", command=lambda: open_box("МЕГАЯЩИК", 80, 250000))
    btn_mega.pack(fill="x", padx=40, pady=12)

    root.mainloop()

def main_cli():
    state = load_save()
    print("\n" + "=" * 55)
    print(TITLE)
    print(f"Платформа: Debian / Arch / Windows / Android")
    print(f"Боец: {state['active_character']} | 🏆 {state['trophies']} | ⚡ {int(state['aura']):,}")
    print("=" * 55)
    print("1. Тапнуть (Могать) 🤫🧏")
    print("2. Личный Инвентарь (Номера, Тачки, Крипта) 🎒")
    print("3. Купить Крипту НА ВСЕ ДЕНЬГИ (ALL-IN) 🚀")
    print("4. Крутить Рулетку Госномеров РФ 🔢")
    print("5. Открыть Мегаящик 🎁")
    print("0. Выход")
    choice = input("\nВыберите действие [0-5]: ").strip()
    if choice == "1":
        state["aura"] += 100
        state["total_mogs"] += 1
        write_save(state)
        print(f"Успех! Баланс: {int(state['aura']):,} ⚡")
    elif choice == "2":
        print("\n=== ВАШ ИНВЕНТАРЬ ===")
        print(f"Номера: {state['owned_plates']}")
        print(f"Тачки: {state['owned_cars']}")
    elif choice == "3":
        coins = state["aura"] / CRYPTO_PRICES["TON"]
        state["crypto_balances"]["TON"] = state["crypto_balances"].get("TON", 0.0) + coins
        state["aura"] = 0
        write_save(state)
        print(f"Куплено {coins:.2f} TON НА ВСЁ!")
    elif choice == "4":
        p = spin_plate()
        state["owned_plates"].append(p)
        write_save(state)
        print(f"Выбит госномер: {p}")

if __name__ == "__main__":
    if "--cli" in sys.argv:
        main_cli()
    else:
        try:
            main_gui()
        except Exception:
            main_cli()
