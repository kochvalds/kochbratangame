#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Looksmax Clicker v2.0.0 — Grand Multiplatform Overhaul Edition (Debian, Arch Linux, Windows, Android)
Features:
- Kyrgyz Anton (Кыргыз Антон)
- 100 Evolution Stages (Stage 0 to Stage 100 Apex Multiverse God)
- Banya Hybrids in regular fighter roster with mega-multipliers
- Corrected starting plate: "О 741 ТР 77" (E333KX can only be won!)
- Full Brawl Stars UI, Crypto ALL-IN, Boxes, Cars, Plates and Inventory!
"""

import os
import sys
import json
import random
import time
from pathlib import Path

TITLE = "🤫🧏 LOOKSMAX CLICKER v2.0.0 — BRAWL STARS & KYRGYZ ANTON EDITION"
SAVE_FILE = Path.home() / ".looksmax_clicker_save.json"

DEFAULT_STATE = {
    "aura": 50.0,
    "total_mogs": 0,
    "stage": 0,
    "tokens": 100,
    "gems": 30,
    "trophies": 0,
    "rank": 1,
    "active_character": "kyrgyz_anton",
    "active_skin": "Базовый Антон (Кыргыз-Сигма)",
    "equipped_car": "vaz_2107",
    "owned_cars": ["vaz_2107"],
    "equipped_plate": "О 741 ТР 77",
    "owned_plates": ["О 741 ТР 77 (Городской)"],
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
    ("mark2_tourerv", "Toyota Mark II 1JZ-GTE (Бишкек Дрифт)", "Toyota", 35000, 0.35),
    ("bmw_m5_f90", "BMW M5 F90 Competition", "BMW", 85000, 0.45),
    ("merc_g63", "Mercedes-AMG G63 Gelik", "Mercedes", 120000, 0.60),
    ("porsche_911", "Porsche 911 GT3 RS", "Porsche", 250000, 0.85),
    ("bugatti_chiron", "Bugatti Chiron Super Sport", "Bugatti", 1000000, 1.50)
]

CHARACTERS = {
    "kyrgyz_anton": {
        "name": "Кыргыз Антон",
        "title": "Кыргызский Сигма-Батыр",
        "quote": "«Кумыс выпил — челюсть в кулак сжал, на Марке по Бишкеку дал!» 🇰🇬🐎",
        "rarity": "ЭПИЧЕСКИЙ",
        "mult": 3.5
    },
    "gleb_sportik": {
        "name": "Глеб Спортик",
        "title": "Чемпион по Турникам & Протеину",
        "quote": "«Пять подходов на брусьях, протеин в шейкере!» 🏋️‍♂️",
        "rarity": "ОБЫЧНЫЙ",
        "mult": 1.2
    },
    "zahar_baryga": {
        "name": "Захар Барыга",
        "title": "Магнат Ресейла & P2P Обнала",
        "quote": "«Оригинальный Poison, чек из Дубая, барсетка трещит!» 👟",
        "rarity": "РЕДКИЙ",
        "mult": 1.5
    },
    "hybrid_gleb_zahar": {
        "name": "Глебо-Захар (Спортивный Спекулянт)",
        "title": "Мифический Банный Гибрид",
        "quote": "«Турники по чеку, кроссовки на брусьях, пар 110 градусов!» 🔥",
        "rarity": "МИФИЧЕСКИЙ (СВЕРХСИЛЬНЫЙ)",
        "mult": 12.0
    },
    "hybrid_anton_gleb": {
        "name": "Антоно-Глеб (Турник-Батыр)",
        "title": "Легендарный Степной Гибрид",
        "quote": "«На Марке к турникам, кумыс вместо изотоника!» ⚡",
        "rarity": "ЛЕГЕНДАРНЫЙ (СВЕРХСИЛЬНЫЙ)",
        "mult": 20.0
    },
    "hybrid_skuf_penisov": {
        "name": "Скуфо-Пенисов 333 (Турбо-Танк)",
        "title": "Хроматический Банный Танк",
        "quote": "«Турбо-Пенисов на танке с пивным дозатором Е333КХ!» 🚗",
        "rarity": "ХРОМАТИЧЕСКИЙ (СВЕРХСИЛЬНЫЙ)",
        "mult": 30.0
    },
    "hybrid_vlados_koch": {
        "name": "Владосо-Коч (Абсолютный Берсерк Бани)",
        "title": "Апекс Бог Русского Пара",
        "quote": "«Каменка пылает, веник свистит, челюсть дробит реальность!» 👑",
        "rarity": "АПЕКС БОГ (УЛЬТРА-МОЩЬ)",
        "mult": 50.0
    }
}

RUSSIAN_REGIONS = ["77", "99", "777", "799", "78", "98", "178", "198", "52", "152", "16", "116", "23", "93", "123", "34", "54", "66", "96", "196"]
CYRILLIC_LETTERS = ["А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"]

def load_save():
    if SAVE_FILE.exists():
        try:
            with open(SAVE_FILE, "r", encoding="utf-8") as f:
                data = json.load(f)
                state = DEFAULT_STATE.copy()
                state.update(data)
                # Fix legacy bug if save had E333KX by default
                if state.get("equipped_plate") == "Е 333 КХ 777" and state.get("total_mogs", 0) < 5:
                    state["equipped_plate"] = "О 741 ТР 77"
                    state["owned_plates"] = ["О 741 ТР 77 (Городской)"]
                return state
        except Exception:
            return DEFAULT_STATE.copy()
    return DEFAULT_STATE.copy()

def write_save(state):
    try:
        with open(SAVE_FILE, "w", encoding="utf-8") as f:
            json.dump(state, f, ensure_ascii=False, indent=2)
    except Exception:
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
    root.title("Looksmax Clicker v2.0.0 — Grand Brawl Stars & Kyrgyz Anton Edition")
    root.geometry("860x650")
    root.configure(bg="#0B0E14")

    DARK_BG = "#0B0E14"
    CARD_BG = "#1A1F2C"
    GOLD = "#FFD700"
    CYAN = "#00E5FF"
    GREEN = "#00E676"
    PURPLE = "#D500F9"

    # Brawl Stars Top Profile Header
    header_frame = tk.Frame(root, bg=CARD_BG, highlightbackground=GOLD, highlightthickness=2, padx=12, pady=8)
    header_frame.pack(fill="x", padx=10, pady=8)

    profile_label = tk.Label(header_frame, text=f"👤 {CHARACTERS.get(state['active_character'], {}).get('name', state['active_character'])} | 🏆 {state['trophies']} | Ранг {state['rank']} | Уровень {state['stage']}/100", font=("Arial", 11, "bold"), fg=GOLD, bg=CARD_BG)
    profile_label.pack(side="left")

    currencies_label = tk.Label(header_frame, text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ {state['tokens']} | 💎 {state['gems']}", font=("Arial", 11, "bold"), fg=CYAN, bg=CARD_BG)
    currencies_label.pack(side="right")

    # Center Tabs (Brawl Stars Style)
    notebook = ttk.Notebook(root)
    notebook.pack(fill="both", expand=True, padx=10, pady=5)

    # TAB 1: BRAWL / MOGGING
    tab_mog = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_mog, text="🥊 БОЙ (МОГГИНГ)")

    mog_aura_lbl = tk.Label(tab_mog, text=f"{int(state['aura']):,} ⚡", font=("Arial", 30, "bold"), fg=CYAN, bg=DARK_BG)
    mog_aura_lbl.pack(pady=12)

    stage_lbl = tk.Label(tab_mog, text=f"Stage {state['stage']}/100: {CHARACTERS.get(state['active_character'], {}).get('title', 'Боец')}", font=("Arial", 12, "bold"), fg=GOLD, bg=DARK_BG)
    stage_lbl.pack(pady=4)

    def on_click():
        mult = CHARACTERS.get(state['active_character'], {}).get("mult", 1.0)
        gain = (1.0 + state["stage"] * 2.5) * mult
        state["aura"] += gain
        state["total_mogs"] += 1
        state["trophies"] = state["total_mogs"] // 5 + len(state["owned_cars"]) * 50 + len(state["owned_plates"]) * 100
        state["rank"] = min(35, state["trophies"] // 250 + 1)
        if random.randint(1, 100) <= 30:
            state["tokens"] += 1
        mog_aura_lbl.config(text=f"{int(state['aura']):,} ⚡")
        currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ {state['tokens']} | 💎 {state['gems']}")
        profile_label.config(text=f"👤 {CHARACTERS.get(state['active_character'], {}).get('name', state['active_character'])} | 🏆 {state['trophies']} | Ранг {state['rank']} | Уровень {state['stage']}/100")
        write_save(state)

    btn_mog = tk.Button(tab_mog, text="🤫 МОГАТЬ (ТАП) 🧏", font=("Arial", 20, "bold"), bg=GOLD, fg="black", activebackground="#FFE082", padx=25, pady=15, command=on_click)
    btn_mog.pack(pady=15)

    def evolve_stage():
        cost = (state["stage"] + 1) * 2000.0 * (1.2 ** min(50, state["stage"]))
        if state["aura"] < cost:
            messagebox.showinfo("Эволюция", f"Необходимо {int(cost):,} ауры для следующего уровня ({state['stage']+1}/100)!")
            return
        if state["stage"] >= 100:
            messagebox.showinfo("Максимум", "Вы достигли Stage 100: Абсолютный Бог Мультивселенной Луксмакса!")
            return
        state["aura"] -= cost
        state["stage"] += 1
        write_save(state)
        stage_lbl.config(text=f"Stage {state['stage']}/100")
        profile_label.config(text=f"👤 {CHARACTERS.get(state['active_character'], {}).get('name', state['active_character'])} | 🏆 {state['trophies']} | Ранг {state['rank']} | Уровень {state['stage']}/100")
        mog_aura_lbl.config(text=f"{int(state['aura']):,} ⚡")
        messagebox.showinfo("LEVEL UP 🌟", f"Поздравляем! Достигнут уровень {state['stage']}/100!")

    btn_evolve = tk.Button(tab_mog, text=f"🌟 ПОВЫСИТЬ STAGE ({state['stage']+1}/100)", font=("Arial", 12, "bold"), bg=PURPLE, fg="white", command=evolve_stage)
    btn_evolve.pack(pady=6)

    # TAB 2: ROSTER (БОЙЦЫ И ГИБРИДЫ)
    tab_roster = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_roster, text="🎭 БОЙЦЫ И ГИБРИДЫ")

    roster_frame = tk.Frame(tab_roster, bg=DARK_BG)
    roster_frame.pack(fill="both", expand=True, padx=10, pady=10)

    for cid, cdata in CHARACTERS.items():
        c_card = tk.Frame(roster_frame, bg=CARD_BG, highlightbackground=GOLD if cid == state["active_character"] else "#37474F", highlightthickness=1, padx=8, pady=6)
        c_card.pack(fill="x", pady=4)
        
        info_txt = f"{cdata['name']} [{cdata['rarity']}] • Сила x{cdata['mult']}\n{cdata['quote']}"
        lbl = tk.Label(c_card, text=info_txt, font=("Arial", 9, "bold"), fg="#FFF", bg=CARD_BG, justify="left")
        lbl.pack(side="left")

        def make_select(char_id=cid):
            def select_char():
                state["active_character"] = char_id
                write_save(state)
                profile_label.config(text=f"👤 {CHARACTERS[char_id]['name']} | 🏆 {state['trophies']} | Ранг {state['rank']} | Уровень {state['stage']}/100")
                messagebox.showinfo("Выбор Бойца", f"Выбран боец: {CHARACTERS[char_id]['name']}!")
            return select_char

        btn_sel = tk.Button(c_card, text="В БОЙ 👑" if cid == state["active_character"] else "ВЫБРАТЬ", bg=GOLD if cid == state["active_character"] else CYAN, fg="black", font=("Arial", 9, "bold"), command=make_select())
        btn_sel.pack(side="right")

    # TAB 3: INVENTORY (ПРЕДМЕТЫ И НОМЕРА)
    tab_inv = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_inv, text="🎒 ИНВЕНТАРЬ")

    inv_text = tk.Text(tab_inv, bg=CARD_BG, fg="#FFFFFF", font=("Consolas", 10), padx=10, pady=10)
    inv_text.pack(fill="both", expand=True, padx=10, pady=10)

    def refresh_inventory():
        inv_text.delete("1.0", "end")
        inv_text.insert("end", "=== 🎒 ЛИЧНЫЙ ИНВЕНТАРЬ БОЙЦА ===\n\n")
        inv_text.insert("end", f"🔢 ГОСНОМЕРА РФ ({len(state['owned_plates'])} шт.):\n")
        for p in state["owned_plates"]:
            active = " [УСТАНОВЛЕН ✅]" if p.startswith(state["equipped_plate"].split(" ")[0]) else ""
            inv_text.insert("end", f"  • {p}{active}\n")
        inv_text.insert("end", f"\n🏎️ АВТОПАРК ({len(state['owned_cars'])} шт.):\n")
        for c in state["owned_cars"]:
            active = " [ВЫБРАНА 👑]" if c == state["equipped_car"] else ""
            inv_text.insert("end", f"  • {c}{active}\n")
        inv_text.insert("end", f"\n🪙 КРИПТО-КОШЕЛЕК:\n")
        for sym, bal in state["crypto_balances"].items():
            inv_text.insert("end", f"  • {sym}: {bal:.4f} (≈ {int(bal * CRYPTO_PRICES[sym]):,} ⚡)\n")

    refresh_inventory()

    # TAB 4: CRYPTO (ALL-IN)
    tab_crypto = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_crypto, text="🪙 КРИПТА (ALL-IN)")

    crypto_lbl = tk.Label(tab_crypto, text="📈 КРИПТОВАЛЮТНАЯ БИРЖА (ПОКУПКА НА ВСЕ ДЕНЬГИ)", font=("Arial", 12, "bold"), fg=CYAN, bg=DARK_BG)
    crypto_lbl.pack(pady=10)

    for sym, price in CRYPTO_PRICES.items():
        frame_coin = tk.Frame(tab_crypto, bg=CARD_BG, padx=8, pady=6)
        frame_coin.pack(fill="x", padx=15, pady=4)

        lbl = tk.Label(frame_coin, text=f"{sym}: {int(price):,} ⚡ | Баланс: {state['crypto_balances'].get(sym, 0.0):.4f}", font=("Arial", 10, "bold"), fg="#FFF", bg=CARD_BG)
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
                l.config(text=f"{coin_sym}: {int(coin_price):,} ⚡ | Баланс: {state['crypto_balances'][coin_sym]:.4f}")
                mog_aura_lbl.config(text=f"{int(state['aura']):,} ⚡")
                currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ {state['tokens']} | 💎 {state['gems']}")
                refresh_inventory()
                messagebox.showinfo("ALL-IN 🚀", f"Куплено {coins:.4f} {coin_sym} НА ВСЕ ДЕНЬГИ!")
            return buy_all

        btn_all_in = tk.Button(frame_coin, text="🚀 НА ВСЕ ДЕНЬГИ", bg=GOLD, fg="black", font=("Arial", 9, "bold"), command=make_buy_all())
        btn_all_in.pack(side="right", padx=5)

    # TAB 5: RUSSIAN PLATES ROULETTE
    tab_plates = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_plates, text="🔢 РУЛЕТКА ГОСНОМЕРОВ")

    cur_plate_lbl = tk.Label(tab_plates, text=f"Текущий номер: {state['equipped_plate']}", font=("Arial", 16, "bold"), fg=GOLD, bg=DARK_BG)
    cur_plate_lbl.pack(pady=20)

    def do_spin():
        cost = 10000.0
        if state["aura"] < cost:
            messagebox.showinfo("Рулетка", f"Нужно {int(cost):,} ауры для выбивания номера!")
            return
        state["aura"] -= cost
        new_p = spin_plate()
        state["owned_plates"].append(new_p)
        state["equipped_plate"] = new_p.split(" ")[0] + " " + new_p.split(" ")[1] + " " + new_p.split(" ")[2] + " " + new_p.split(" ")[3]
        write_save(state)
        cur_plate_lbl.config(text=f"Текущий номер: {state['equipped_plate']}")
        refresh_inventory()
        currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ {state['tokens']} | 💎 {state['gems']}")
        mog_aura_lbl.config(text=f"{int(state['aura']):,} ⚡")
        messagebox.showinfo("ГОСНОМЕР ВЫБИТ!", f"Вам выпал госномер:\n{new_p}!")

    btn_spin = tk.Button(tab_plates, text="🚗 ВЫБИТЬ НОМЕР (10,000 Ауры)", font=("Arial", 14, "bold"), bg=CYAN, fg="black", command=do_spin)
    btn_spin.pack(pady=10)

    # TAB 6: BRAWL BOXES
    tab_boxes = tk.Frame(notebook, bg=DARK_BG)
    notebook.add(tab_boxes, text="🎁 МЕГАЯЩИКИ")

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
        currencies_label.config(text=f"⚡ Аура: {int(state['aura']):,} | 🎟️ {state['tokens']} | 💎 {state['gems']}")
        messagebox.showinfo(box_name, f"🎉 ВЫПАЛО ИЗ ЯЩИКА:\n• +{aura_win:,} ⚡ Ауры\n• +{tokens_win} 🎟️ Жетонов!")

    btn_brawl = tk.Button(tab_boxes, text="📦 Бро-Бокс (100 Жетонов)", font=("Arial", 11, "bold"), bg="#4CAF50", fg="white", command=lambda: open_box("Бро-Бокс", 0, 15000))
    btn_brawl.pack(fill="x", padx=40, pady=8)

    btn_big = tk.Button(tab_boxes, text="💼 Большой Ящик (30 Гемов)", font=("Arial", 11, "bold"), bg="#9C27B0", fg="white", command=lambda: open_box("Большой Ящик", 30, 60000))
    btn_big.pack(fill="x", padx=40, pady=8)

    btn_mega = tk.Button(tab_boxes, text="👑 МЕГАЯЩИК (80 Гемов)", font=("Arial", 13, "bold"), bg=GOLD, fg="black", command=lambda: open_box("МЕГАЯЩИК", 80, 250000))
    btn_mega.pack(fill="x", padx=40, pady=12)

    root.mainloop()

def main_cli():
    state = load_save()
    print("\n" + "=" * 60)
    print(TITLE)
    print(f"Платформа: Debian / Arch (.pkg.tar.zst) / Windows / Android")
    print(f"Боец: {CHARACTERS.get(state['active_character'], {}).get('name', state['active_character'])} | Stage {state['stage']}/100 | 🏆 {state['trophies']} | ⚡ {int(state['aura']):,}")
    print(f"Госномер: {state['equipped_plate']}")
    print("=" * 60)
    print("1. Тапнуть (Могать) 🤫🧏")
    print("2. Повысить Уровень (Stage 0 -> 100) 🌟")
    print("3. Личный Инвентарь (Номера, Тачки, Крипта) 🎒")
    print("4. Купить Крипту НА ВСЕ ДЕНЬГИ (ALL-IN) 🚀")
    print("5. Крутить Рулетку Госномеров РФ 🔢")
    print("6. Открыть Мегаящик 🎁")
    print("0. Выход")
    choice = input("\nВыберите действие [0-6]: ").strip()
    if choice == "1":
        mult = CHARACTERS.get(state['active_character'], {}).get("mult", 1.0)
        gain = 100 * mult
        state["aura"] += gain
        state["total_mogs"] += 1
        write_save(state)
        print(f"Успех! Баланс: {int(state['aura']):,} ⚡ (Бонус x{mult})")
    elif choice == "2":
        state["stage"] = min(100, state["stage"] + 1)
        write_save(state)
        print(f"Уровень повышен до Stage {state['stage']}/100!")
    elif choice == "3":
        print("\n=== ВАШ ИНВЕНТАРЬ ===")
        print(f"Номера: {state['owned_plates']}")
        print(f"Тачки: {state['owned_cars']}")
    elif choice == "4":
        coins = state["aura"] / CRYPTO_PRICES["TON"]
        state["crypto_balances"]["TON"] = state["crypto_balances"].get("TON", 0.0) + coins
        state["aura"] = 0
        write_save(state)
        print(f"Куплено {coins:.2f} TON НА ВСЁ!")
    elif choice == "5":
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
