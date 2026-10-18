# Silah Modu (Fabric, Minecraft 1.21.11)

Minecraft'a 4 silah ekler: **Keskin Nişancı, RPG, Revolver, Bıçak**.

## Kurulum (oynamak için)

1. Minecraft 1.21.11 için [Fabric Loader](https://fabricmc.net/use/installer/) kur.
2. [Fabric API](https://modrinth.com/mod/fabric-api) (1.21.11 sürümü) dosyasını `mods` klasörüne at.
3. Bu modun `.jar` dosyasını da `mods` klasörüne at.

`.jar` dosyasını GitHub'dan almak için: repo > **Actions** > son çalışma > **Artifacts**.

## Kontroller

| Tuş | İşlev |
|---|---|
| Sol tık | Ateş et / bıçakla vur |
| Sağ tık (basılı tut) | Sniper'da scope aç (2.5x) |
| **R** | Şarjör doldur |
| **B** | Silah menüsü (Creative sekmesi "Silahlar" da var) |

Tuşlar Ayarlar > Kontroller > "Silah Modu" altından değiştirilebilir.

## Silahlar (1 kalp = 2 HP)

| Silah | Şarjör / Yedek | Hasar |
|---|---|---|
| Keskin Nişancı | 3 / 12 | Kafa 200 HP, gövde 5 kalp |
| RPG | 3 / 18 | 5x5 alana 5 kalp; tam kafa isabetinde 7,5 kalp |
| Revolver | 8 / 32 | Kafa 200 HP, gövde 4 kalp |
| Bıçak | - | Arkadan 200 HP, önden gövde 3 kalp, önden kafa 6 kalp |

Silahların dayanıklılığı yoktur (sınırsız). RPG roketi giderken arkasından ateş ve duman bırakır.

## Ayarlar

Tüm hasar, mermi, bekleme ve menzil değerleri tek dosyada:
`src/main/java/com/silahmod/WeaponConfig.java`

Belirtmediğin değerler (atış hızı, doldurma süresi, menzil) benim varsayımımdır; oradan değiştir.

## Derleme (kendi bilgisayarında)

JDK 21 gerekir.

```
./gradlew build
```

Çıktı: `build/libs/silah-modu-1.0.0.jar` (`-sources` olmayan dosya).

## GitHub'a yükleme

```
git init
git add .
git commit -m "Silah modu"
git branch -M main
git remote add origin https://github.com/KULLANICI/REPO.git
git push -u origin main
```

Her push'ta `.github/workflows/build.yml` modu otomatik derler.

## Proje yapısı

- `src/main` : ortak/sunucu kodu (silahlar, hasar, mermi, ağ paketleri)
- `src/client` : sadece istemci (scope, mermi sayacı, B menüsü, zoom)
- `src/main/resources/assets/silahmod` : dokular, modeller, dil dosyaları (TR/EN)
