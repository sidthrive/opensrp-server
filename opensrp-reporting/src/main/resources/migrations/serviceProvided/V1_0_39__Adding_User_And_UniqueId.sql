INSERT INTO report.unique_id(anm_id, last_value) VALUES ((SELECT ID FROM report.dim_anm WHERE anmidentifier='demo1'), 100);

INSERT INTO report.unique_id(anm_id, last_value) VALUES ((SELECT ID FROM report.dim_anm WHERE anmidentifier='demo2'), 200);

INSERT INTO report.unique_id(anm_id, last_value) VALUES ((SELECT ID FROM report.dim_anm WHERE anmidentifier='demo3'), 300);