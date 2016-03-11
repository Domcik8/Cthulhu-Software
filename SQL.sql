--Following scripts drop all tables.
drop table SystemParameter;
drop table ServiceMultiselect;
drop table Reservation;
drop table Service;
drop table Payment;
drop table Person;
drop table Role;
drop table Type;
drop table object;

CREATE Table Object
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    InternalName    VARCHAR(255)                UNIQUE,
    TypeID          BIGINT          NOT NULL,
    IsDeleted       INTEGER,
    CreatedDate     DATE,
    CreatedBy       BIGINT,
    DeletedDate     DATE,
    DeletedBy       BIGINT,
    OPT_LOCK_VERSION INTEGER,
    PRIMARY KEY (ID)
);

CREATE TABLE Type
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID        BIGINT          NOT NULL    UNIQUE,
    Title           VARCHAR(255)    NOT NULL    UNIQUE,
    Description     VARCHAR(255),
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (ObjectID) REFERENCES Object (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Person
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID        BIGINT          NOT NULL        UNIQUE,
    Username        VARCHAR(255)    NOT NULL        UNIQUE,
    Password        VARCHAR(255)    NOT NULL,
    Role            BIGINT          NOT NULL,
    Priority        INTEGER         NOT NULL,
    Points          DECIMAL         NOT NULL,
    FacebookID      BIGINT,
    FirstName       VARCHAR(255),
    MiddleName      VARCHAR(255),
    LastName        VARCHAR(255),
    Address         VARCHAR(255),
    PersonalID      VARCHAR(255)    NOT NULL        UNIQUE,
    MembershipDue   DATE            NOT NULL,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (ObjectID) REFERENCES Object (ID),
    FOREIGN KEY (Role) REFERENCES Object (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Role
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID            BIGINT          NOT NULL        UNIQUE,
    Title               VARCHAR(255)    NOT NULL        UNIQUE,
    Description         VARCHAR(255),
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (ObjectID) REFERENCES Object (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Payment
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID            BIGINT          NOT NULL        UNIQUE,
    PaymentReg          VARCHAR(255)    NOT NULL        UNIQUE,
    PrincipalID         BIGINT          NOT NULL,
    PrincipalVersionID  BIGINT          NOT NULL,
    PaymentPrice        DECIMAL         NOT NULL,
    PaymentDate         Date            NOT NULL,
    PaidWithMoney       INTEGER         NOT NULL,
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (ObjectID) REFERENCES Object (ID),
    FOREIGN KEY (PrincipalID) REFERENCES Object (ID),
    FOREIGN KEY (PrincipalVersionID) REFERENCES Principal (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Service
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID        BIGINT         NOT NULL         UNIQUE,
    ServiceReg      VARCHAR(255)   NOT NULL         UNIQUE,
    IsActive        INTEGER        NOT NULL,
    StartDate       Date           NOT NULL,
    EndDate         Date           NOT NULL,
    WeekPrice       Decimal        NOT NULL,
    NumberOfPlaces  INTEGER,
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (ObjectID)  REFERENCES Object (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Reservation
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID            BIGINT          NOT NULL    UNIQUE,
    ReservationReg      VARCHAR(255)    NOT NULL    UNIQUE,
    ServiceID           BIGINT          NOT NULL,
    ServiceVersionID    BIGINT          NOT NULL,
    PersonID            BIGINT          NOT NULL,
    PersonVersionID     BIGINT          NOT NULL,
    StartDate           Date            NOT NULL,
    EndDate             Date            NOT NULL,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (ObjectID) REFERENCES Object (ID),
    FOREIGN KEY (ServiceID) REFERENCES Object (ID),
    FOREIGN KEY (PersonID) REFERENCES Object (ID),
    FOREIGN KEY (ServiceVersionID) REFERENCES Service (ID),
    FOREIGN KEY (PersonVersionID) REFERENCES Person (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE ServiceMultiselect
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID         BIGINT         NOT NULL        UNIQUE,
    ParentID         BIGINT         NOT NULL,
    ParentVersionID  BIGINT         NOT NULL,
    ChildID          BIGINT         NOT NULL,
    ChildVersionID   BIGINT         NOT NULL,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (ObjectID)  REFERENCES Object (ID),
    FOREIGN KEY (ParentID)  REFERENCES Object (ID),
    FOREIGN KEY (ChildID)   REFERENCES Object (ID),
    FOREIGN KEY (ParentVersionID)  REFERENCES Service (ID),
    FOREIGN KEY (ChildVersionID)   REFERENCES Service (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE SystemParameter
(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ObjectID        BIGINT          NOT NULL        UNIQUE, 
    Title           VARCHAR(255)    NOT NULL        UNIQUE,
    Description     VARCHAR(255),
    Value           INT             NOT NULL,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (ObjectID)  REFERENCES Object (ID),
    PRIMARY KEY (ID)
);

ALTER TABLE Object ADD CONSTRAINT Object_TypeID_To_Type_ID  FOREIGN KEY (TypeID) REFERENCES Object (ID);
ALTER TABLE Object ADD CONSTRAINT Object_CreatedByID_To_Type_ID  FOREIGN KEY (CreatedBy) REFERENCES Object (ID);
ALTER TABLE Object ADD CONSTRAINT Object_DeletedBY_To_Type_ID  FOREIGN KEY (DeletedBy) REFERENCES Object (ID);