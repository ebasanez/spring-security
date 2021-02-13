# This script should be executed manualy

# ACL Tables

create table if not exists acl_sid (
id bigint unsigned not null auto_increment primary key,
principal boolean not null COMMENT 'Tessl us is sid is an actual PRINCIPAL (user) or something else (ej: ROLE)',
sid varchar(199) not null COMMENT 'UserId (when this sid is for a principal)',
unique key unique_asl_sid(sid, principal)
)engine = InnoDB;

CREATE TABLE IF NOT EXISTS acl_class (
id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
class VARCHAR(100) NOT NULL,
UNIQUE KEY uk_acl_class (class)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS acl_object_identity (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class BIGINT UNSIGNED NOT NULL,
    object_id_identity BIGINT NOT NULL,
    parent_object BIGINT UNSIGNED,
    owner_sid BIGINT UNSIGNED,
    entries_inheriting BOOLEAN NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class , object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object)
        REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class)
        REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid)
        REFERENCES acl_sid (id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS acl_entry (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED NOT NULL,
    ace_order INTEGER NOT NULL,
    sid BIGINT UNSIGNED NOT NULL,
    mask INTEGER UNSIGNED NOT NULL COMMENT 'Persmissions to be used (read, write, delete.....)',
    granting BOOLEAN NOT NULL,
    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity , ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity)
        REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid)
        REFERENCES acl_sid (id)
)  ENGINE=INNODB;





# Initial test records

# Create 3 possessions belonging to user 1
INSERT INTO possession (id, name, owner_id) VALUES
(1,'possession1', 1),
(2,'possession2', 1),
(3,'possession3', 1);

# Define sids fo users (prerequesites: users with usernames: kike, juan)
INSERT INTO acl_sid (id, principal, sid) VALUES
(1,1,'kike'),
(2,1,'juan');

# Declare Posession entity as protected class
INSERT INTO acl_class (id, class) VALUES
(1, 'com.example.demo.model.Possession');

# Link our acl_sid to protected classes
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, owner_sid, entries_inheriting) VALUES
(1, 1 , 1 , 1 , 1),
(2, 1 , 2 , 1 , 1),
(3, 1 , 3 , 1 , 1);

# Insert the actual acl entries, that define how each acl_sid can interact (permissions mask) with each protected entity
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 0, 1, 16, 1, 0, 0), # kike has Admin permission for kike Possession 1
(2, 2, 0, 1, 16, 1, 0, 0), # kike has Admin permission for shared Possession 2
(3, 2, 1, 2,  1, 1, 0, 0), # juan has Read permission for shared Possession 2
(4, 3, 0, 2, 16, 1, 0, 0); # juan has Admin permission for kike Possession 3