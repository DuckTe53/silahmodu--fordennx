package com.silahmod;

/**
 * Tum silah ayarlari burada. Sayilari degistirip tekrar derlemen yeterli.
 *
 * Hasar birimi: HP. 1 kalp = 2 HP (5 kalp = 10 HP).
 * Sure birimi: tick. 20 tick = 1 saniye.
 */
public final class WeaponConfig {
	private WeaponConfig() {}

	public static float hearts(float hearts) {
		return hearts * 2.0F;
	}

	// ---------------- KESKIN NISANCI ----------------
	public static final int SNIPER_MAG = 3;            // sarjor
	public static final int SNIPER_RESERVE = 12;       // yedek mermi
	public static final float SNIPER_HEAD = 200.0F;    // kafa
	public static final float SNIPER_BODY = hearts(5); // govde = 10 HP
	public static final float SNIPER_ZOOM = 2.5F;      // scope yakinlastirma (2.5x)
	public static final double SNIPER_RANGE = 300.0;
	public static final int SNIPER_FIRE_DELAY = 30;    // atislar arasi (1.5 sn) - benim varsayimim
	public static final int SNIPER_RELOAD = 70;        // doldurma suresi - benim varsayimim
	public static final int SNIPER_RELOAD_FRAMES = 10; // reload animasyon kare sayisi (tools/gen_models.py ile ayni olmali)
	public static final boolean SNIPER_GLINT = true;   // durbun acan oyuncunun durbununde, BASKALARININ gordugu isik
	public static final int SNIPER_GLINT_INTERVAL = 2; // isik kac tickte bir parlar (kucuk = daha sik)
	public static final int SNIPER_GLINT_COLOR = 0xFFF2B0; // isik rengi (RGB). Kirmizi istersen 0xFF2020
	public static final float SNIPER_GLINT_SIZE = 1.3F;    // isik buyuklugu

	// ---------------- RPG ----------------
	public static final int RPG_MAG = 3;
	public static final int RPG_RESERVE = 18;
	public static final float RPG_BLAST = hearts(5);       // alan hasari = 10 HP
	public static final float RPG_DIRECT_HEAD = hearts(7.5F); // tam kafa = 15 HP
	public static final double RPG_AREA = 5.0;             // 5x5x5 blokluk alan
	public static final boolean RPG_HURTS_SHOOTER = true;  // false yaparsan kendine zarar vermez
	public static final double RPG_SPEED = 1.6;            // blok/tick
	public static final int RPG_MAX_AGE = 120;             // 6 sn sonra kendiliginden patlar
	public static final int RPG_FIRE_DELAY = 40;
	public static final int RPG_RELOAD = 80;
	public static final int RPG_RELOAD_FRAMES = 10;

	// ---------------- REVOLVER ----------------
	public static final int REVOLVER_MAG = 8;
	public static final int REVOLVER_RESERVE = 32;
	public static final float REVOLVER_HEAD = 200.0F;
	public static final float REVOLVER_BODY = hearts(4);   // 8 HP
	public static final double REVOLVER_RANGE = 120.0;
	public static final int REVOLVER_FIRE_DELAY = 8;
	public static final int REVOLVER_RELOAD = 50;
	public static final int REVOLVER_RELOAD_FRAMES = 9;

	// ---------------- ZIRHLAR ----------------
	// Kask 1 (yesil): demir kask gibi (2 zirh) + RPG'nin tam kafa isabetini azaltir. Azaltma orani benim varsayimim.
	public static final double HELMET_1_ARMOR = 2.0;
	public static final double HELMET_1_TOUGHNESS = 0.0;
	public static final float HELMET_1_RPG_HEAD_REDUCTION = 0.25F;   // RPG tam kafa hasari %25 azalir

	// Yelek 1 (gri kamuflaj): tum silahlarin govde hasarini azaltir
	public static final float VEST_1_BODY_REDUCTION = 0.15F;         // %15

	// Kask 2 (koyu, siperlikli): Koruma II buyulu elmas pantolon gibi (6 zirh, 2 sertlik, Koruma II) + altin kalp
	public static final double HELMET_2_ARMOR = 6.0;
	public static final double HELMET_2_TOUGHNESS = 2.0;
	public static final int HELMET_2_PROTECTION_LEVEL = 2;           // Koruma buyusu seviyesi
	public static final int HELMET_2_GOLD_HEARTS = 2;                // giyince verilen altin kalp

	// Yelek 2 (kahverengi): tum silahlarin govde hasarini azaltir
	public static final float VEST_2_BODY_REDUCTION = 0.30F;         // %30

	// ---------------- EL BOMBASI ----------------
	// Sag tikla firlatilir (survivalda 1 adet harcar). Fitil bitince patlar; blok kirmaz, sadece hasar verir.
	public static final int GRENADE_FUSE = 60;               // patlama suresi (tick). 20 tick = 1 saniye
	public static final double GRENADE_SPEED = 1.1;          // firlatma hizi
	public static final double GRENADE_RADIUS = 5.0;         // patlama yaricapi (blok)
	public static final float GRENADE_DAMAGE = hearts(10);   // merkezdeki hasar = 20 HP; uzaklastikca azalir (en az %25)
	public static final boolean GRENADE_HURTS_THROWER = true; // false = firlatan kendi bombasindan zarar gormez
	public static final int GRENADE_COOLDOWN = 20;           // iki firlatma arasi (tick)
	public static final int GRENADE_MAX_CARRY = 4;           // en fazla tasinabilecek (B menusu da buna kadar verir)

	// ---------------- MED KIT ----------------
	// Sag tik BASILI tutulur, sure dolunca can verir ve 1 adet harcanir (survivalda). Hareket yavaslar.
	public static final float MEDKIT_HEAL = hearts(6);       // 12 HP = 6 kalp
	public static final int MEDKIT_USE_TICKS = 60;           // kullanma suresi (tick) = 3 saniye
	public static final int MEDKIT_MAX_CARRY = 4;            // en fazla tasinabilecek

	// ---------------- BICAK ----------------
	public static final float KNIFE_BACKSTAB = 200.0F;     // arkadan
	public static final float KNIFE_FRONT = hearts(3);     // onden govde = 6 HP
	public static final float KNIFE_HEAD = hearts(6);      // onden kafa = 12 HP
	public static final int KNIFE_COOLDOWN = 10;
	/** Hedefin arkasindan sayilma acisi: 0.5 = yaklasik 60 derece koni. */
	public static final double KNIFE_BACK_DOT = 0.5;
}
