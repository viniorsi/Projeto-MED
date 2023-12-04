alter table pacientes add ativo tinyInt;
update pacientes set ativo = 1;
