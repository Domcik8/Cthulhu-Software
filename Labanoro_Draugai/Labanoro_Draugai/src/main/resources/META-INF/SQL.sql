--Following scripts drop all tables.
drop view  GroupView;
drop table PersonRegistrationForm;
drop table FormAttribute;
drop table ServiceImage;
drop table HouseImage;
drop table ServicePictures;
drop table HousePictures;
drop table SystemParameter;
drop table MultiselectReservationToService;
drop table MultiselectHouseToService;
drop table Recommendation;
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
    OptLockVersion  INTEGER,
    PRIMARY KEY (ID)
);

CREATE TABLE Person
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Email               VARCHAR(255)                UNIQUE,
    Password            VARCHAR(255),
    TypeID              INTEGER         NOT NULL,
    Priority            INTEGER,
    Points              DECIMAL(10,2),
    FacebookID          VARCHAR(255),
    FacebookPassword    VARCHAR(255),
    FirstName           VARCHAR(255),
    MiddleName          VARCHAR(255),
    LastName            VARCHAR(255),
    Address             VARCHAR(255),
    PersonalID          VARCHAR(255)                UNIQUE,
    MembershipDue       DATE,
    RecommendationsReceived INTEGER,
    RecommendationsToSend   INTEGER,
    EmailConfirmation   VARCHAR(255)                UNIQUE,
    UniqueKey           VARCHAR(255)                UNIQUE,
    IsDeleted           BOOLEAN,
    OptLockVersion      INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Recommendation
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID              INTEGER         NOT NULL,
    RecommendedID       INTEGER         NOT NULL,
    RecommenderID       INTEGER         NOT NULL,
    RecommendationDate  DATE,
    OptLockVersion      INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    FOREIGN KEY (RecommendedID) REFERENCES Person (ID),
    FOREIGN KEY (RecommenderID) REFERENCES Person (ID),
    PRIMARY KEY (ID),
    CONSTRAINT uq_yourtablename UNIQUE(RecommendedID, RecommenderID)
);

CREATE TABLE Payment
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID              INTEGER         NOT NULL,
    PaymentReg          VARCHAR(255)                    UNIQUE,
    PersonID            INTEGER         NOT NULL,
    PaymentPrice        Decimal(19,4),
    PaymentDate         DATE,
    ApprovedDate        DATE,
    IsDeleted           BOOLEAN,
    OptLockVersion      INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    FOREIGN KEY (PersonID) REFERENCES Person (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Service
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Title           VARCHAR(255),
    Description     VARCHAR(255),
    TypeID          INTEGER        NOT NULL,
    ServiceReg      VARCHAR(255)   NOT NULL      UNIQUE,
    IsActive        BOOLEAN,
    SeasonStartDate DATE,
    SeasonEndDate   DATE,
    WeekPrice       Decimal(19,4),
    NumberOfPlaces  INTEGER,
    IsDeleted       BOOLEAN,
    OptLockVersion  INTEGER,
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
    WeekPrice       Decimal(19,4),
    NumberOfPlaces  INTEGER,
    IsDeleted       BOOLEAN,
    OptLockVersion  INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE Reservation
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID              INTEGER         NOT NULL,
    ReservationReg      VARCHAR(255)                UNIQUE,
    HouseID             INTEGER         NOT NULL,
    PersonID            INTEGER         NOT NULL,
    PaymentID           INTEGER         NOT NULL,
    StartDate           DATE,
    EndDate             DATE,
    IsDeleted           BOOLEAN,
    OptLockVersion      INTEGER,
    FOREIGN KEY (TypeID) REFERENCES Type (ID),
    FOREIGN KEY (HouseID) REFERENCES House (ID),
    FOREIGN KEY (PersonID) REFERENCES Person (ID),
    FOREIGN KEY (PaymentID) REFERENCES Payment (ID),
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
    Title           VARCHAR(255)    NOT NULL,
    Value           VARCHAR(255)    NOT NULL,
    Description     VARCHAR(255),
    IsDeleted       BOOLEAN,
    OptLockVersion  INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE HouseImage
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID          INTEGER         NOT NULL, 
    HouseID         INTEGER         NOT NULL,
    InternalName    VARCHAR(255)    NOT NULL    UNIQUE,
    Sequence        INTEGER,
    Image           BLOB            NOT NULL,
    MIMEType        VARCHAR(255)    NOT NULL,
    Description     VARCHAR(255),
    IsDeleted       BOOLEAN,
    OptLockVersion  INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    FOREIGN KEY (HouseID)  REFERENCES House (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE FormAttribute
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID          INTEGER         NOT NULL, 
    InternalName    VARCHAR(255)    NOT NULL    UNIQUE,
    Name            VARCHAR(255)    NOT NULL,
    ListItems       VARCHAR(255),
    IsRequired      BOOLEAN,
    Description     VARCHAR(255),
    IsDeleted       BOOLEAN,
    OptLockVersion  INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    PRIMARY KEY (ID)
);

CREATE TABLE PersonRegistrationForm
(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TypeID          INTEGER         NOT NULL, 
    InternalName    VARCHAR(255)    NOT NULL    UNIQUE,
    PersonID        INTEGER         NOT NULL    UNIQUE,
    FormValue       LONG VARCHAR,
    IsDeleted       BOOLEAN,
    OptLockVersion  INTEGER,
    FOREIGN KEY (TypeID)  REFERENCES Type (ID),
    FOREIGN KEY (PersonID)  REFERENCES Person (ID),
    PRIMARY KEY (ID)
);

CREATE VIEW GroupView (Email, InternalName) AS
    SELECT Email, InternalName FROM LABANORODB.Person, LABANORODB."TYPE"
    WHERE Person.TYPEID = "TYPE".ID;