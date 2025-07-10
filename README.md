# ✈️ AirFi India

A modern Android app built with Jetpack Compose that displays airline details across India. Users can search, favorite, and view airline information.  
Data is locally fetched from a **JSON file stored in the `assets` folder** — no external API calls required!

---

## 📸 Screenshots

<div align="center">
  <img src="https://github.com/user-attachments/assets/1c2ee14f-e04b-4649-8df0-d166b70d6a43" width="300"/>
  <img src="https://github.com/user-attachments/assets/74ea928f-fa81-44fa-b87b-db829b35d1ce" width="300"/>
  <img src="https://github.com/user-attachments/assets/5e322422-7e99-492c-810b-9c15751ebcc4" width="300"/>
  <img src="https://github.com/user-attachments/assets/02facc17-a76b-47dc-acb7-62d84736d054" width="300"/>
</div>

---

## 🚀 Features

- 🔍 **View Airline List** – Complete list of airlines from local JSON
- ❤️ **Favorite Airlines** – Save airlines you frequently visit
- 🔎 **Search Functionality** – Find airlines by name instantly
- 🖼️ **Jetpack Compose UI** – Fully built using modern Compose toolkit
- 📁 **Local JSON Source** – Reads airline data from `assets/airlines.json`

---

## 📁 Data Source

This project uses a local JSON file for airline data.  
📂 **Path**: `app/src/main/assets/airlines.json`

Example:
```json
[
  {
    "id": "1",
    "name": "EasyJet",
    "country": "United Kingdom",
    "headquarters": "London Luton Airport",
    "fleet_size": 342,
    "website": "https://www.easyjet.com",
    "logo_url": "https://upload.wikimedia.org/wikipedia/commons/6/65/EasyJet_logo.svg"
  }
]
