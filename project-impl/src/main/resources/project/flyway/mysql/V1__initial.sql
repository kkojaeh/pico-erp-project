create table pjt_project (
	id binary(16) not null,
	attachment_id binary(50),
	comment_subject_id varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	customer_id varchar(50),
	customer_manager_contact_email varchar(60),
	customer_manager_contact_fax_number varchar(40),
	customer_manager_contact_mobile_phone_number varchar(40),
	customer_manager_contact_name varchar(50),
	customer_manager_contact_telephone_number varchar(40),
	customer_name varchar(50),
	description longtext,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	manager_id varchar(50),
	manager_name varchar(50),
	name varchar(50),
	primary key (id)
) engine=InnoDB;

create table pjt_project_charge (
	id binary(16) not null,
	charged bit,
	charged_date datetime,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	name varchar(50),
	paid bit,
	paid_date datetime,
	quantity decimal(19,2),
	unit_price decimal(19,2),
	project_id binary(16),
	primary key (id)
) engine=InnoDB;

create table pjt_project_sale_item (
	id binary(16) not null,
	charged_quantity decimal(19,2),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	delivered_quantity decimal(19,2),
	expiration_date datetime,
	expired bit not null,
	expired_date datetime,
	item_id binary(16),
	ordered_quantity decimal(19,2),
	paid_quantity decimal(19,2),
	unit_price decimal(19,2),
	project_id binary(16),
	primary key (id)
) engine=InnoDB;

alter table pjt_project_sale_item
	add constraint PJT_PROJECT_SALE_ITEM_ITEM_ID_IDX unique (project_id,item_id);

alter table pjt_project_charge
	add constraint FKadx7fj09xsi8ktuol73yv1qvf foreign key (project_id)
	references pjt_project (id);

alter table pjt_project_sale_item
	add constraint FKt1hlm0fdnk68nvjw0woy4s266 foreign key (project_id)
	references pjt_project (id);
