
create table pjt_project_charge (
	id varchar(50) not null,
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
	project_id varchar(50),
	primary key (id)
) engine=InnoDB;

create table pjt_project_sale_item (
	id varchar(50) not null,
	charged_count decimal(19,2),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	delivered_count decimal(19,2),
	item_id varchar(50),
	ordered_count decimal(19,2),
	paid_count decimal(19,2),
	unit_price decimal(19,2),
	project_id varchar(50),
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
