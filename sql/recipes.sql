INSERT INTO recipes (id, result_id, quantity)
VALUES
    /**********************************************************************/
    /*                        # LEVEL 5 RECIPES #                         */
    /**********************************************************************/

    /* Polymerization Preparation */
    (3501, 30115, 1),
    /* Bipolar Nanoflake */
    (3502, 30125, 1),
    /* D32 Steel */
    (3503, 30135, 1),
    /* Crystalline Electronic Unit */
    (3504, 30145, 1),
    /* Sintered Core Crystals */
    (3505, 30155, 1),

    /**********************************************************************/
    /*                        # LEVEL 4 RECIPES #                         */
    /**********************************************************************/

    /* Orirock Concentration */
    (3401, 30014, 1),
    /* Sugar Lump */
    (3402, 30024, 1),
    /* Polyester Lump */
    (3403, 30034, 1),
    /* Oriron Block */
    (3404, 30044, 1),
    /* Keton Colloid */
    (3405, 30054, 1),
    /* Optimized device */
    (3406, 30064, 1),
    /* White Horse Kohl */
    (3407, 30074, 1),
    /* Manganese Trihydrate */
    (3408, 30084, 1),
    /* Grindstone Pentahydrate */
    (3409, 30094, 1),
    /* RMA70-24 */
    (3410, 30104, 1),
    /* Polymerized Gel */
    (3411, 31014, 1),
    /* Incandescent Alloy Block */
    (3412, 31024, 1),
    /* Crystalline Circuit */
    (3413, 31034, 1),
    /* Refined Solvent */
    (3414, 31044, 1),
    /* Cutting Fluid Solution */
    (3415, 31054, 1),
    /* Transmuted Salt Agglomerate */
    (3416, 31064, 1),

    /**********************************************************************/
    /*                        # LEVEL 3 RECIPES #                         */
    /**********************************************************************/

    /* Orirock Cluster */
    (3301, 30013, 1),
    /* Sugar Pack */
    (3302, 30023, 1),
    /* Polyester Pack */
    (3303, 30033, 1),
    /* Oriron Cluster */
    (3304, 30043, 1),
    /* Aketon */
    (3305, 30053, 1),
    /* Integrated Device */
    (3306, 30063, 1),

    /**********************************************************************/
    /*                        # LEVEL 2 RECIPES #                         */
    /**********************************************************************/

    /* Orirock Cube */
    (3201, 30012, 1),
    /* Sugar */
    (3202, 30022, 1),
    /* Polyester */
    (3203, 30032, 1),
    /* Oriron */
    (3204, 30042, 1),
    /* Polyketon */
    (3205, 30052, 1),
    /* Device */
    (3206, 30062, 1),

    /**********************************************************************/
    /*                     # SKILL SUMMARY RECIPES #                      */
    /**********************************************************************/

    /* Skill Summary - 2 */
     (31302, 3302, 1),
    /* Skill Summary - 3 */
     (31303, 3303, 1),

    /**********************************************************************/
    /*                         # CHIP RECIPES #                           */
    /**********************************************************************/

    /* Vanguard Chip */
    (31211, 3211, 2),
    /* Vanguard Chip Pack */
    (31212, 3212, 2),
    /* Guard Chip */
    (31221, 3221, 2),
    /* Guard Chip Pack */
    (31222, 3222, 2),
    /* Defender Chip */
    (31231, 3231, 2),
    /* Defender Chip Pack */
    (31232, 3232, 2),
    /* Sniper Chip */
    (31241, 3241, 2),
    /* Sniper Chip Pack */
    (31242, 3242, 2),
    /* Caster Chip */
    (31251, 3251, 2),
    /* Caster Chip Pack */
    (31252, 3252, 2),
    /* Medic Chip */
    (31261, 3261, 2),
    /* Medic Chip Pack */
    (31262, 3262, 2),
    /* Supporter Chip */
    (31271, 3271, 2),
    /* Supporter Chip Pack */
    (31272, 3272, 2),
    /* Specialist Chip */
    (31281, 3281, 2),
    /* Specialist Chip Pack */
    (31282, 3282, 2)

ON CONFLICT (id) DO UPDATE
    SET result_id = excluded.result_id,
        quantity = excluded.quantity
;
