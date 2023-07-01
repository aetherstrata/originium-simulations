CREATE MATERIALIZED VIEW item_capabilities AS
SELECT i.id                                                     AS item_id,
       (SELECT (EXISTS (SELECT r.quantity,
                               r.id,
                               r.result_id
                        FROM recipes r
                        WHERE r.result_id = i.id)) AS "exists") AS craftable,
       (SELECT (EXISTS (SELECT d.quantity,
                               d.standard_deviation,
                               d.times,
                               d.id,
                               d.item_id,
                               d.stage_id
                        FROM drops d
                        WHERE d.item_id = i.id)) AS "exists")   AS farmable
FROM items i;
