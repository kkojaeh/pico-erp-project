ALTER TABLE pjt_project_sale_item ADD expiration_date datetime NULL;
ALTER TABLE pjt_project_sale_item ADD expired bit not NULL default 0;
ALTER TABLE pjt_project_sale_item ADD expired_date datetime NULL;
