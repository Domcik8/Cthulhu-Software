--Following scripts drop all tables.
drop table ServicePictures;
drop table HousePictures;
drop table SystemParameter;
drop table MultiselectReservationToService;
drop table MultiselectHouseToService;
drop table Reservation;
drop table Service;
drop table Payment;
drop table Person;
drop table House;
drop table Role;
drop table Type;
drop table ObjectTable;

CREATE TABLE Type
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    InternalName    VARCHAR(255)                UNIQUE,
    Title           VARCHAR(255),
    Description     VARCHAR(255),
    IsDeleted       BOOLEAN,
    OPT_LOCK_VERSION INTEGER,
    PRIMARY KEY (ID)
);

CREATE TABLE Person
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Email               VARCHAR(255)                UNIQUE,
    Password            VARCHAR(255),
    TypeID              INTEGER         NOT NULL,
    Priority            INTEGER,
    Points              DECIMAL,
    FacebookID          VARCHAR(255),
    FacebookAccessToken VARCHAR(255),
    FirstName           VARCHAR(255),
    MiddleName          VARCHAR(255),
    LastName            VARCHAR(255),
    Address             VARCHAR(255),
    PersonalID          VARCHAR(255)                UNIQUE,
    MembershipDue       DATE,
    IsDeleted           BOOLEAN,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

/*CREATE TABLE Role
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID              INTEGER         NOT NULL,
    Title               VARCHAR(255)                    UNIQUE,
    Description         VARCHAR(255),
    IsDeleted           BOOLEAN,
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    PRIMARY KEY (ID)
);*/

CREATE TABLE Payment
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID              INTEGER         NOT NULL,
    PaymentReg          VARCHAR(255)                    UNIQUE,
    PersonID            INTEGER         NOT NULL,
    PaymentPrice        DECIMAL,
    PaymentDate         Date,
    PaidWithMoney       INTEGER,
    IsDeleted           BOOLEAN,
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    FOREIGN KEY (PersonID) REFERENCES Person (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Service
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Title           VARCHAR(255),
    Description         VARCHAR(255),
    TypeID          INTEGER        NOT NULL,
    ServiceReg      VARCHAR(255)   NOT NULL      UNIQUE,
    IsActive        BOOLEAN,
    SeasonStartDate DATE,
    SeasonEndDate   DATE,
    WeekPrice       Decimal,
    NumberOfPlaces  INTEGER,
    IsDeleted       BOOLEAN,
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE House
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Title           VARCHAR(255),
    TypeID          INTEGER        NOT NULL,
    Description     VARCHAR(255),
    HouseReg        VARCHAR(255)   NOT NULL     UNIQUE,
    Address         VARCHAR(255),
    IsActive        BOOLEAN,
    SeasonStartDate DATE,
    SeasonEndDate   DATE,
    WeekPrice       Decimal,
    NumberOfPlaces  INTEGER,
    IsDeleted       BOOLEAN,
    OPT_LOCK_VERSION    INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Reservation
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID              INTEGER         NOT NULL,
    ReservationReg      VARCHAR(255)               UNIQUE,
    HouseID             INTEGER         NOT NULL,
    PersonID            INTEGER         NOT NULL,
    StartDate           DATE,
    EndDate             DATE,
    IsDeleted           BOOLEAN,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    FOREIGN KEY (HouseID) REFERENCES House (ID),
    FOREIGN KEY (PersonID) REFERENCES Person (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE MultiselectReservationToService
(
    ParentID        INTEGER         NOT NULL,
    ChildID         INTEGER         NOT NULL,
    FOREIGN KEY (ParentID)  REFERENCES Reservation (ID),
    FOREIGN KEY (ChildID)   REFERENCES Service (ID),
    PRIMARY KEY (ParentID, ChildID)
);

CREATE TABLE MultiselectHouseToService
(
    ParentID        INTEGER         NOT NULL,
    ChildID         INTEGER         NOT NULL,
    FOREIGN KEY (ParentID)  REFERENCES House (ID),
    FOREIGN KEY (ChildID)   REFERENCES Service (ID),
    PRIMARY KEY (ParentID, ChildID)
);

CREATE TABLE SystemParameter
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID          INTEGER         NOT NULL, 
    InternalName    VARCHAR(255)    NOT NULL    UNIQUE,
    Title           VARCHAR(255),
    Description     VARCHAR(255),
    Value           INTEGER,
    IsDeleted       BOOLEAN,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE HousePictures
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID          INTEGER         NOT NULL, 
    HouseID         INTEGER,
    Sequence        INTEGER,
    Path            VARCHAR(255)    NOT NULL,
    IsDeleted       BOOLEAN,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    FOREIGN KEY (HouseID)  REFERENCES House (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE ServicePictures
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID          INTEGER         NOT NULL, 
    ServiceID       INTEGER,
    Sequence        INTEGER,
    Path            VARCHAR(255)    NOT NULL,
    IsDeleted       BOOLEAN,
    OPT_LOCK_VERSION INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    FOREIGN KEY (ServiceID)  REFERENCES Service (ID),
    PRIMARY KEY (ID)
);