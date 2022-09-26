-- partner
CREATE SEQUENCE partners_seq;
create table partners
(
    id            bigint primary key default nextval('partners_seq'),
    partner_token varchar(255) not null,
    partner_name  varchar(255) not null,
    business_no   varchar(255) not null,
    email         varchar(255) not null,
    status        varchar(30)  not null default 'ENABLE',
    created_at    timestamp(6) not null,
    updated_at    timestamp(6) null
);

COMMENT ON COLUMN partners.id IS 'ID';
COMMENT ON COLUMN partners.partner_token IS 'partner_token';
COMMENT ON COLUMN partners.partner_name IS '파트너명';
COMMENT ON COLUMN partners.business_no IS '사업자등록번호';
COMMENT ON COLUMN partners.email IS 'email';
COMMENT ON COLUMN partners.status IS '상태';
COMMENT ON COLUMN partners.created_at IS '생성 일시';
COMMENT ON COLUMN partners.updated_at IS '수정 일시';
COMMENT ON TABLE partners IS 'partners';

create
index partner_idx01 on partners (partner_token);

create
index partner_idx02 on partners (created_at);

create
index partner_idx03 on partners (updated_at);

-- item
CREATE SEQUENCE items_seq;
create table items
(
    id         bigint primary key default nextval('items_seq'),
    item_token varchar(255) not null,
    partner_id bigint       not null,
    item_name  varchar(255) not null,
    item_price integer not null,
    status     varchar(30)  not null default 'PREPARE',
    deleted_at timestamp(6) null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) null
);

COMMENT ON COLUMN items.id IS 'ID';
COMMENT ON COLUMN items.item_token IS 'item_token';
COMMENT ON COLUMN items.partner_id IS '파트너명';
COMMENT ON COLUMN items.item_name IS '상품명';
COMMENT ON COLUMN items.item_price IS '상품 가격';
COMMENT ON COLUMN items.status IS '상태';
COMMENT ON COLUMN items.deleted_at IS '삭제 일시';
COMMENT ON COLUMN items.created_at IS '생성 일시';
COMMENT ON COLUMN items.updated_at IS '수정 일시';
COMMENT ON TABLE items IS 'items';

create
index item_idx01 on items (item_token);

create
index item_idx02 on items (partner_id);

create
index item_idx03 on items (created_at);

create
index item_idx04 on items (updated_at);

create
index item_idx05 on items (deleted_at);


-- item_option_group
CREATE SEQUENCE item_option_group_seq;
create table item_option_groups
(
    id                     bigint primary key default nextval('item_option_group_seq'),
    item_id                bigint      not null,
    ordering               smallint not null,
    item_option_group_name varchar(30) not null,
    created_at             timestamp(6) not null,
    updated_at             timestamp(6) null
);

COMMENT ON COLUMN item_option_groups.id IS 'ID';
COMMENT ON COLUMN item_option_groups.item_id IS '상품 ID';
COMMENT ON COLUMN item_option_groups.ordering IS '정렬순서';
COMMENT ON COLUMN item_option_groups.item_option_group_name IS '옵션그룹명 (색상, 사이즈 등)';
COMMENT ON COLUMN item_option_groups.created_at IS '생성 일시';
COMMENT ON COLUMN item_option_groups.updated_at IS '수정 일시';
COMMENT ON TABLE item_option_groups IS 'item_option_groups';

create
index item_option_group_idx01 on item_option_groups (item_id);

create
index item_option_group_idx02 on item_option_groups (created_at);

create
index item_option_group_idx03 on item_option_groups (updated_at);


-- item_option
CREATE SEQUENCE item_options_seq;
create table item_options
(
    id                   bigint primary key default nextval('item_options_seq'),
    item_option_group_id bigint      not null,
    ordering             smallint not null,
    item_option_name     varchar(30) not null,
    item_option_price    integer not null,
    created_at           timestamp(6) not null,
    updated_at           timestamp(6) null
);

COMMENT ON COLUMN item_options.id IS 'ID';
COMMENT ON COLUMN item_options.item_option_group_id IS '상품 옵션 그룹 ID';
COMMENT ON COLUMN item_options.ordering IS '정렬순서';
COMMENT ON COLUMN item_options.item_option_name IS '옵션명 (빨강, 파랑, XL, L)';
COMMENT ON COLUMN item_options.item_option_price IS '상품 옵션 가격';
COMMENT ON COLUMN item_options.created_at IS '생성 일시';
COMMENT ON COLUMN item_options.updated_at IS '수정 일시';
COMMENT ON TABLE item_options IS 'item_option_groups';

create
index item_option_idx01 on item_options (item_option_group_id);

create
index item_option_idx02 on item_options (created_at);

create
index item_option_idx03 on item_options (updated_at);


-- order
CREATE SEQUENCE orders_seq;
create table orders
(
    id                bigint primary key default nextval('orders_seq'),
    order_token       varchar(255) not null,
    user_id           bigint       not null,
    pay_method        varchar(30)  not null,
    receiver_name     varchar(30)  not null,
    receiver_phone    varchar(30)  not null,
    receiver_zipcode  varchar(10)  not null,
    receiver_address1 varchar(255) not null,
    receiver_address2 varchar(255) not null,
    etc_message       varchar(255) not null,
    status            varchar(30)  not null default 'INIT',
    ordered_at        timestamp(6) not null,
    created_at        timestamp(6) not null,
    updated_at        timestamp(6) null
);

COMMENT ON COLUMN orders.id IS 'ID';
COMMENT ON COLUMN orders.order_token IS 'order_token';
COMMENT ON COLUMN orders.user_id IS '유저 ID';
COMMENT ON COLUMN orders.pay_method IS '결제수단';
COMMENT ON COLUMN orders.receiver_name IS '수령자명';
COMMENT ON COLUMN orders.receiver_phone IS '수령자 휴대폰번호';
COMMENT ON COLUMN orders.receiver_zipcode IS '수령자 우편번호';
COMMENT ON COLUMN orders.receiver_address1 IS '수령자 주소1';
COMMENT ON COLUMN orders.receiver_address2 IS '수령자 주소2';
COMMENT ON COLUMN orders.etc_message IS '남기는 말';
COMMENT ON COLUMN orders.status IS '상태';
COMMENT ON COLUMN orders.ordered_at IS '주문 일시';
COMMENT ON COLUMN orders.created_at IS '생성 일시';
COMMENT ON COLUMN orders.updated_at IS '수정 일시';
COMMENT ON TABLE orders IS 'orders';


create
index order_idx01 on orders (order_token);

create
index order_idx02 on orders (user_id);

create
index order_idx03 on orders (ordered_at);

create
index order_idx04 on orders (created_at);

create
index order_idx05 on orders (updated_at);


-- order_items
CREATE SEQUENCE order_items_seq;
create table order_items
(
    id              bigint primary key default nextval('order_items_seq'),
    order_id        bigint       not null,
    order_count     smallint      not null,
    partner_id      bigint       not null,
    item_id         bigint       not null,
    item_name       varchar(255) not null,
    item_token      varchar(30)  not null,
    item_price      integer not null,
    delivery_status varchar(30)  not null default 'BEFORE_DELIVERY',
    created_at      timestamp(6) not null,
    updated_at      timestamp(6) null
);

COMMENT ON COLUMN order_items.id IS 'ID';
COMMENT ON COLUMN order_items.order_id IS 'order_id';
COMMENT ON COLUMN order_items.order_count IS '주문갯수';
COMMENT ON COLUMN order_items.partner_id IS '파트너 ID';
COMMENT ON COLUMN order_items.item_id IS '상품 ID';
COMMENT ON COLUMN order_items.item_name IS '상품명';
COMMENT ON COLUMN order_items.item_token IS '상품 token';
COMMENT ON COLUMN order_items.item_price IS '상품 가격';
COMMENT ON COLUMN order_items.delivery_status IS '상태';
COMMENT ON COLUMN order_items.created_at IS '생성 일시';
COMMENT ON COLUMN order_items.updated_at IS '수정 일시';
COMMENT ON TABLE order_items IS 'order_items';

create
index order_item_idx01 on order_items (order_id);

create
index order_item_idx02 on order_items (partner_id);

create
index order_item_idx03 on order_items (item_id);

create
index order_item_idx04 on order_items (item_token);

create
index order_item_idx05 on order_items (created_at);

create
index order_item_idx06 on order_items (updated_at);


-- order_item_option_groups
CREATE SEQUENCE order_item_option_groups_seq;
create table order_item_option_groups
(
    id                     bigint primary key default nextval('order_item_option_groups_seq'),
    order_item_id          bigint       not null,
    ordering               smallint not null,
    item_option_group_name varchar(255) not null,
    created_at             timestamp(6) not null,
    updated_at             timestamp(6) null
);

COMMENT ON COLUMN order_item_option_groups.id IS 'ID';
COMMENT ON COLUMN order_item_option_groups.order_item_id IS 'order_item_id';
COMMENT ON COLUMN order_item_option_groups.ordering IS '정렬순서';
COMMENT ON COLUMN order_item_option_groups.item_option_group_name IS '상품 옵션 그룹명';
COMMENT ON COLUMN order_item_option_groups.created_at IS '생성 일시';
COMMENT ON COLUMN order_item_option_groups.updated_at IS '수정 일시';
COMMENT ON TABLE order_item_option_groups IS 'order_item_option_groups';

create
index order_item_option_groups_idx01 on order_item_option_groups (order_item_id);

create
index order_item_option_groups_idx02 on order_item_option_groups (created_at);

create
index order_item_option_groups_idx03 on order_item_option_groups (updated_at);


-- order_item_options
CREATE SEQUENCE order_item_options_seq;
create table order_item_options
(
    id                         bigint primary key default nextval('order_item_options_seq'),
    order_item_option_group_id bigint       not null,
    ordering                   smallint not null,
    item_option_name           varchar(255) not null,
    item_option_price          integer not null,
    created_at                 timestamp(6) not null,
    updated_at                 timestamp(6) null
);
COMMENT ON COLUMN order_item_options.id IS 'ID';
COMMENT ON COLUMN order_item_options.order_item_option_group_id IS 'order_item_option_group_id';
COMMENT ON COLUMN order_item_options.ordering IS '정렬순서';
COMMENT ON COLUMN order_item_options.item_option_name IS '상품 옵션명';
COMMENT ON COLUMN order_item_options.item_option_price IS '상품 옵션 가격';
COMMENT ON COLUMN order_item_options.created_at IS '생성 일시';
COMMENT ON COLUMN order_item_options.updated_at IS '수정 일시';
COMMENT ON TABLE order_item_option_groups IS 'order_item_options';

create
index order_item_options_idx01 on order_item_options (order_item_option_group_id);

create
index order_item_options_idx02 on order_item_options (created_at);

create
index order_item_options_idx03 on order_item_options (updated_at);