-- ============================================================
-- DDL-MYSQL.sql — Esquema BackDeporteParaTodos para MySQL 8.0+
-- Migrado desde Oracle (DROP-CREATE.sql + AQLActual.sql)
-- ============================================================
-- Conversiones aplicadas:
--   VARCHAR2(N BYTE) -> VARCHAR(N)
--   NUMBER(*,0) PK con secuencia -> BIGINT AUTO_INCREMENT
--   NUMBER(*,0) general -> BIGINT
--   NUMBER(1) -> TINYINT
--   NUMBER(3) -> SMALLINT
--   BLOB -> LONGBLOB
--   Oracle DATE + java.sql.Date/LocalDate -> DATE
--   Oracle DATE + java.sql.Timestamp -> DATETIME
--   Secuencias eliminadas (SEQ_CLS_CODIGO, SEQ_ID_HORARIO,
--                          SEQ_ID_IMAGEN, SEQ_ESC_ID)
--   ALTER TABLE CUR_ESTADO_INSCRIPCIONES y GRP_PERIODO integrados
--   Directivas SQL*Plus eliminadas (REM, SET DEFINE OFF, COMMENT ON)
--   NOTIFICACION_ENTRADA omitida (sin CREATE TABLE en el DDL de origen)
-- ------------------------------------------------------------
-- SCRUM-166: correccion de 15 conflictos NOT NULL + ON DELETE SET NULL
--   (Error 1830 en MySQL; Oracle no valida esto en DDL time)
--   Patron aplicado por caso:
--     CASCADE (6):  #1 INSTRUCTOR, #6 ALUMNO, #7/#8 INTERMEDIA,
--                   #11 HORARIO, #15 ALERTA
--     RESTRICT (8): #2 CURSO, #3 GRUPO, #5 CLASE-grupo,
--                   #9/#10 ASISTENCIA, #12/#13 INSCRIPCION, #14 NOTIFICACION
--     Quitar NOT NULL + SET NULL (1): #4 CLASE.PERF_ID (instructor opcional)
-- ------------------------------------------------------------
-- HALLAZGO ADICIONAL SCRUM-166 (no corregido, documentado):
--   TBL_COORDINADOR.PERF_ID es NOT NULL pero NO tiene FK hacia TBL_PERFIL.
--   Inconsistencia preexistente en el DDL de Oracle -- no introducida
--   por esta migracion. Pendiente de decision de diseno.
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- --------------------------------------------------------
-- DROP (orden inverso a dependencias, FOREIGN_KEY_CHECKS=0)
-- --------------------------------------------------------
DROP TABLE IF EXISTS TBL_ALERTA;
DROP TABLE IF EXISTS TBL_NOTIFICACION;
DROP TABLE IF EXISTS TBL_BANDEJA_ENTRADA;
DROP TABLE IF EXISTS TBL_BANDEJA_SALIDA;
DROP TABLE IF EXISTS TBL_ASISTENCIA;
DROP TABLE IF EXISTS TBL_INSCRIPCION;
DROP TABLE IF EXISTS TBL_HORARIO;
DROP TABLE IF EXISTS TBL_CLASE;
DROP TABLE IF EXISTS TBL_INTERMEDIA_ALUMNO_PROGRAMA;
DROP TABLE IF EXISTS TBL_ALUMNO;
DROP TABLE IF EXISTS TBL_GRUPO;
DROP TABLE IF EXISTS TBL_CURSO;
DROP TABLE IF EXISTS TBL_COORDINADOR;
DROP TABLE IF EXISTS TBL_INSTRUCTOR;
DROP TABLE IF EXISTS TBL_PROGRAMA;
DROP TABLE IF EXISTS TBL_FACULTAD;
DROP TABLE IF EXISTS TBL_CATEGORIA_CURSO;
DROP TABLE IF EXISTS TBL_PERFIL;
DROP TABLE IF EXISTS TBL_IMAGEN;
DROP TABLE IF EXISTS TBL_DEPORTE;
DROP TABLE IF EXISTS TBL_ESCENARIO;
DROP TABLE IF EXISTS NOTIFICACION_ENTRADA;

SET FOREIGN_KEY_CHECKS = 1;

-- --------------------------------------------------------
-- TBL_DEPORTE
-- --------------------------------------------------------
CREATE TABLE TBL_DEPORTE (
    META_ELIMINADO INT          NOT NULL,
    DEPT_NOMBRE    VARCHAR(100) NOT NULL,
    CONSTRAINT PK_TBL_DEPORTE PRIMARY KEY (DEPT_NOMBRE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_IMAGEN
-- IMG_ID: era SEQ_ID_IMAGEN (START 201) -> AUTO_INCREMENT
-- IMG_DATOS: BLOB -> LONGBLOB
-- --------------------------------------------------------
CREATE TABLE TBL_IMAGEN (
    META_ELIMINADO   INT          NOT NULL,
    IMG_ID           BIGINT       NOT NULL AUTO_INCREMENT,
    IMG_NOMBRE       VARCHAR(200) NOT NULL,
    IMG_TIPO_ARCHIVO VARCHAR(100) NOT NULL,
    IMG_LONGITUD     BIGINT       NOT NULL,
    IMG_DATOS        LONGBLOB     NOT NULL,
    CONSTRAINT PK_TBL_IMAGEN PRIMARY KEY (IMG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_CATEGORIA_CURSO
-- FK_CATEGORIA_CURSO_IMAGEN: imagen es opcional -> SET NULL OK (columna nullable)
-- --------------------------------------------------------
CREATE TABLE TBL_CATEGORIA_CURSO (
    META_ELIMINADO  INT           NOT NULL,
    CAT_TITULO      VARCHAR(100)  NOT NULL,
    CAT_DESCRIPCION VARCHAR(1000) NOT NULL,
    CAT_IMAGEN      BIGINT,
    CONSTRAINT PK_TBL_CATEGORIA_CURSO    PRIMARY KEY (CAT_TITULO),
    CONSTRAINT FK_CATEGORIA_CURSO_IMAGEN FOREIGN KEY (CAT_IMAGEN) REFERENCES TBL_IMAGEN (IMG_ID) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_PERFIL
-- FK_PERFIL_IMAGEN: sin ON DELETE -> RESTRICT por defecto (imagen no deberia borrarse)
-- --------------------------------------------------------
CREATE TABLE TBL_PERFIL (
    META_ELIMINADO INT          NOT NULL,
    PERF_ID        VARCHAR(50)  NOT NULL,
    PERF_NOMBRE    VARCHAR(100) NOT NULL,
    PERF_CORREO    VARCHAR(100) NOT NULL,
    PERF_IMAGEN    BIGINT,
    PERF_TIPOID    VARCHAR(50)  NOT NULL,
    PERF_SEXO      VARCHAR(10)  NOT NULL,
    CONSTRAINT CKC_TBL_PERF_SEXO   CHECK (PERF_SEXO   IN ('M', 'F')),
    CONSTRAINT CKC_TBL_PERF_TIPOID CHECK (PERF_TIPOID IN ('CC', 'TI', 'CE', 'PP', 'PEP', 'DIE')),
    CONSTRAINT FK_PERFIL_IMAGEN     FOREIGN KEY (PERF_IMAGEN) REFERENCES TBL_IMAGEN (IMG_ID),
    CONSTRAINT PK_TBL_PERFIL        PRIMARY KEY (PERF_ID),
    CONSTRAINT UQ_PERFIL_CORREO     UNIQUE (PERF_CORREO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_INSTRUCTOR
-- SCRUM-166 #1: PERF_ID es PK -> SET NULL imposible -> CASCADE
--   Un instructor es una especializacion de perfil; si el perfil
--   se borra fisicamente, el registro de instructor no tiene existencia propia.
-- --------------------------------------------------------
CREATE TABLE TBL_INSTRUCTOR (
    META_ELIMINADO INT         NOT NULL,
    PERF_ID        VARCHAR(50) NOT NULL,
    CONSTRAINT FK_INSTRUCTOR_PERFIL FOREIGN KEY (PERF_ID) REFERENCES TBL_PERFIL (PERF_ID) ON DELETE CASCADE,
    CONSTRAINT PF_TBL_INSTRUCTOR    PRIMARY KEY (PERF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_FACULTAD
-- --------------------------------------------------------
CREATE TABLE TBL_FACULTAD (
    META_ELIMINADO INT          NOT NULL,
    FAC_NOMBRE     VARCHAR(200),
    CONSTRAINT PK_TBL_FACULTAD PRIMARY KEY (FAC_NOMBRE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_PROGRAMA
-- FK_PROGRAMA_FACULTAD: FAC_NOMBRE es nullable -> SET NULL OK
-- --------------------------------------------------------
CREATE TABLE TBL_PROGRAMA (
    META_ELIMINADO INT          NOT NULL,
    PRG_NOMBRE     VARCHAR(200) NOT NULL,
    FAC_NOMBRE     VARCHAR(200),
    CONSTRAINT PK_TBL_PROGRAMA      PRIMARY KEY (PRG_NOMBRE),
    CONSTRAINT FK_PROGRAMA_FACULTAD FOREIGN KEY (FAC_NOMBRE) REFERENCES TBL_FACULTAD (FAC_NOMBRE) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_CURSO
-- SCRUM-166 #2: CAT_TITULO NOT NULL y parte de PK -> SET NULL imposible -> RESTRICT
--   La categoria es co-PK del curso; no puede quedar sin categoria.
--   El sistema usa soft-delete: el borrado fisico de categorias no deberia ocurrir.
-- FK_TBL_CURSO_DEPORTE: DEPT_NOMBRE nullable -> SET NULL OK
-- FK_TBL_CURSO_IMAGEN:  CUR_IMAGEN nullable -> SET NULL OK
-- CUR_ESTADO_INSCRIPCIONES: integrado desde ALTER TABLE del DDL original
-- --------------------------------------------------------
CREATE TABLE TBL_CURSO (
    META_ELIMINADO           INT           NOT NULL,
    CUR_NOMBRE               VARCHAR(100)  NOT NULL,
    DEPT_NOMBRE              VARCHAR(100),
    CAT_TITULO               VARCHAR(100)  NOT NULL,
    CUR_DESCRIPCION          VARCHAR(1000) NOT NULL,
    CUR_IMAGEN               BIGINT,
    CUR_ESTADO_INSCRIPCIONES VARCHAR(10)   DEFAULT 'ABIERTO',
    CONSTRAINT FK_TBL_CURSO_DEPORTE   FOREIGN KEY (DEPT_NOMBRE) REFERENCES TBL_DEPORTE (DEPT_NOMBRE)        ON DELETE SET NULL,
    CONSTRAINT FK_TBL_CURSO_IMAGEN    FOREIGN KEY (CUR_IMAGEN)  REFERENCES TBL_IMAGEN (IMG_ID)              ON DELETE SET NULL,
    CONSTRAINT FK_TBL_CURSO_CATEGORIA FOREIGN KEY (CAT_TITULO)  REFERENCES TBL_CATEGORIA_CURSO (CAT_TITULO) ON DELETE RESTRICT,
    CONSTRAINT PK_TBL_CURSO           PRIMARY KEY (CUR_NOMBRE, CAT_TITULO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_GRUPO
-- SCRUM-166 #3: CUR_NOMBRE, CAT_TITULO son parte de PK (implicitement NOT NULL)
--   -> SET NULL imposible -> RESTRICT
--   El grupo no existe sin curso. El curso no se borra fisicamente.
-- FK_GRUPO_IMAGEN:     GRP_IMAGEN nullable -> SET NULL OK
-- FK_GRUPO_INSTRUCTOR: sin ON DELETE -> RESTRICT por defecto (instructor puede cambiar)
-- GRP_PERIODO: integrado desde ALTER TABLE del DDL original; NUMBER(1) -> TINYINT
-- GRP_FECHACREACION etc: Java usa LocalDate -> MySQL DATE
-- --------------------------------------------------------
CREATE TABLE TBL_GRUPO (
    META_ELIMINADO             BIGINT,
    GRP_ANIO                   BIGINT,
    GRP_ITERABLE               BIGINT,
    CUR_NOMBRE                 VARCHAR(100),
    CAT_TITULO                 VARCHAR(100),
    GRP_IMAGEN                 BIGINT,
    GRP_CUPOS                  BIGINT,
    GRP_FECHACREACION          DATE,
    GRP_FECHA_FINALIZACION     DATE,
    GRP_FECHA_INSCRIP_APERTURA DATE,
    GRP_FECHA_INSCRIP_CIERRE   DATE,
    PERF_ID                    VARCHAR(50),
    GRP_PERIODO                TINYINT      DEFAULT 1 NOT NULL,
    CONSTRAINT FK_GRUPO_IMAGEN     FOREIGN KEY (GRP_IMAGEN)             REFERENCES TBL_IMAGEN (IMG_ID)                      ON DELETE SET NULL,
    CONSTRAINT FK_GRUPO_CURSO      FOREIGN KEY (CUR_NOMBRE, CAT_TITULO) REFERENCES TBL_CURSO (CUR_NOMBRE, CAT_TITULO)       ON DELETE RESTRICT,
    CONSTRAINT FK_GRUPO_INSTRUCTOR FOREIGN KEY (PERF_ID)                REFERENCES TBL_INSTRUCTOR (PERF_ID),
    CONSTRAINT PK_TBL_GRUPO        PRIMARY KEY (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_CLASE
-- CLS_CODIGO: era SEQ_CLS_CODIGO (START 121) -> AUTO_INCREMENT
-- CLS_FECHA: Java usa java.sql.Date -> MySQL DATE
--   (la hora se gestiona en TBL_HORARIO; comentario Oracle obsoleto)
-- SCRUM-166 #4: PERF_ID -> se quita NOT NULL + se mantiene SET NULL
--   Una clase puede quedar sin instructor asignado (instructor que se da de baja).
--   NULL es semanticamente valido y la entidad Java no tiene nullable=false.
-- SCRUM-166 #5: FK_CLASE_GRUPO con columnas NOT NULL -> RESTRICT
--   Una clase sin grupo no tiene sentido; el grupo no se borra fisicamente.
-- --------------------------------------------------------
CREATE TABLE TBL_CLASE (
    META_ELIMINADO       INT          NOT NULL,
    CLS_CODIGO           BIGINT       NOT NULL AUTO_INCREMENT,
    PERF_ID              VARCHAR(50),
    CLS_FECHA            DATE         NOT NULL,
    CLS_DURACION_HORAS   INT          NOT NULL,
    CLS_DURACION_MINUTOS INT          NOT NULL,
    CLS_OBSERVACION      VARCHAR(1000),
    GRP_ANIO             BIGINT       NOT NULL,
    GRP_ITERABLE         BIGINT       NOT NULL,
    CUR_NOMBRE           VARCHAR(100) NOT NULL,
    CAT_TITULO           VARCHAR(100) NOT NULL,
    CONSTRAINT FK_CLASE_INSTRUCTOR FOREIGN KEY (PERF_ID)                                        REFERENCES TBL_INSTRUCTOR (PERF_ID)                                    ON DELETE SET NULL,
    CONSTRAINT FK_CLASE_GRUPO      FOREIGN KEY (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) REFERENCES TBL_GRUPO (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) ON DELETE RESTRICT,
    CONSTRAINT PK_TBL_CLASE        PRIMARY KEY (CLS_CODIGO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_ALUMNO
-- SCRUM-166 #6: PERF_ID es PK -> SET NULL imposible -> CASCADE
--   Un alumno es una especializacion de perfil; si el perfil
--   se borra fisicamente, el registro de alumno no tiene existencia propia.
-- --------------------------------------------------------
CREATE TABLE TBL_ALUMNO (
    META_ELIMINADO INT         NOT NULL,
    PERF_ID        VARCHAR(50) NOT NULL,
    ALM_CODIGO     VARCHAR(20),
    ALM_TIPO       VARCHAR(20) NOT NULL,
    ALM_ESTADO     INT,
    CONSTRAINT CKC_ALM_TIPO_ALUM CHECK (ALM_TIPO IN ('Estudiante', 'Administrativo', 'Docente')),
    CONSTRAINT FK_ALUMNO_PERFIL  FOREIGN KEY (PERF_ID) REFERENCES TBL_PERFIL (PERF_ID) ON DELETE CASCADE,
    CONSTRAINT PERF_ID           PRIMARY KEY (PERF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_INTERMEDIA_ALUMNO_PROGRAMA
-- SCRUM-166 #7: PRG_NOMBRE parte de PK -> SET NULL imposible -> CASCADE
--   Si el programa desaparece, la relacion alumno-programa pierde significado.
-- SCRUM-166 #8: PERF_ID parte de PK -> SET NULL imposible -> CASCADE
--   Si el alumno desaparece, sus relaciones con programas tambien.
-- --------------------------------------------------------
CREATE TABLE TBL_INTERMEDIA_ALUMNO_PROGRAMA (
    META_ELIMINADO INT          NOT NULL,
    PERF_ID        VARCHAR(50)  NOT NULL,
    PRG_NOMBRE     VARCHAR(200) NOT NULL,
    CONSTRAINT PK_INTERMEDIA_ALUMNO_PROGRAMA PRIMARY KEY (PERF_ID, PRG_NOMBRE),
    CONSTRAINT FK_INTERMEDIA_PROGRAMA FOREIGN KEY (PRG_NOMBRE) REFERENCES TBL_PROGRAMA (PRG_NOMBRE) ON DELETE CASCADE,
    CONSTRAINT FK_INTERMEDIA_ALUMNO   FOREIGN KEY (PERF_ID)    REFERENCES TBL_ALUMNO (PERF_ID)      ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_ASISTENCIA
-- SCRUM-166 #9:  CLS_CODIGO parte de PK -> SET NULL imposible -> RESTRICT
--   La asistencia es registro historico; no se debe borrar una clase
--   que ya tiene asistencias registradas.
-- SCRUM-166 #10: PERF_ID parte de PK -> SET NULL imposible -> RESTRICT
--   El historial de asistencia de un alumno debe preservarse como evidencia.
-- --------------------------------------------------------
CREATE TABLE TBL_ASISTENCIA (
    META_ELIMINADO INT         NOT NULL,
    PERF_ID        VARCHAR(50) NOT NULL,
    CLS_CODIGO     BIGINT      NOT NULL,
    CONSTRAINT FK_ASISTENCIA_CLASE  FOREIGN KEY (CLS_CODIGO) REFERENCES TBL_CLASE (CLS_CODIGO)  ON DELETE RESTRICT,
    CONSTRAINT FK_ASISTENCIA_ALUMNO FOREIGN KEY (PERF_ID)    REFERENCES TBL_ALUMNO (PERF_ID)    ON DELETE RESTRICT,
    CONSTRAINT PK_TBL_ASISTENCIA    PRIMARY KEY (PERF_ID, CLS_CODIGO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_HORARIO
-- HR_ID: era SEQ_ID_HORARIO (START 1) -> AUTO_INCREMENT
-- HR_ESCENARIO: almacena nombre del escenario (VARCHAR), sin FK a TBL_ESCENARIO
-- SCRUM-166 #11: columnas de FK son NOT NULL -> SET NULL imposible -> CASCADE
--   Un horario sin grupo no tiene razon de existir.
--   Si el grupo se elimina fisicamente, sus horarios deben eliminarse.
-- --------------------------------------------------------
CREATE TABLE TBL_HORARIO (
    META_ELIMINADO INT          NOT NULL,
    HR_ID          BIGINT       NOT NULL AUTO_INCREMENT,
    CAT_TITULO     VARCHAR(100) NOT NULL,
    CUR_NOMBRE     VARCHAR(100) NOT NULL,
    GRP_ANIO       BIGINT       NOT NULL,
    GRP_ITERABLE   BIGINT       NOT NULL,
    HR_DIA         VARCHAR(50)  NOT NULL,
    HR_HORAINICIO  VARCHAR(8)   NOT NULL,
    HR_HORAFIN     VARCHAR(8)   NOT NULL,
    HR_ESCENARIO   VARCHAR(100) NOT NULL,
    CONSTRAINT PK_TBL_HORARIO   PRIMARY KEY (HR_ID),
    CONSTRAINT FK_HORARIO_GRUPO FOREIGN KEY (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) REFERENCES TBL_GRUPO (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_INSCRIPCION
-- INSCR_FECHAINSCRIPCION, INSCR_FECHADESVINCULACION:
--   Java usa java.sql.Timestamp con @Temporal(TIMESTAMP) -> MySQL DATETIME
-- SCRUM-166 #12: PERF_ID parte de PK -> SET NULL imposible -> RESTRICT
--   Las inscripciones son registros academicos formales; no se debe
--   borrar un alumno con inscripciones activas o historicas.
-- SCRUM-166 #13: CAT_TITULO/CUR_NOMBRE/GRP_ANIO/GRP_ITERABLE todos PK -> RESTRICT
--   Un grupo con inscripciones no puede borrarse fisicamente.
-- --------------------------------------------------------
CREATE TABLE TBL_INSCRIPCION (
    META_ELIMINADO            INT          NOT NULL,
    INSCR_FECHAINSCRIPCION    DATETIME     NOT NULL,
    INSCR_FECHADESVINCULACION DATETIME,
    GRP_ANIO                  BIGINT       NOT NULL,
    GRP_ITERABLE              BIGINT       NOT NULL,
    CUR_NOMBRE                VARCHAR(100) NOT NULL,
    CAT_TITULO                VARCHAR(100) NOT NULL,
    PERF_ID                   VARCHAR(50)  NOT NULL,
    CONSTRAINT FK_INSCRIPCION_ALUMNO FOREIGN KEY (PERF_ID)                                        REFERENCES TBL_ALUMNO (PERF_ID)                                        ON DELETE RESTRICT,
    CONSTRAINT FK_INSCRIPCION_GRUPO  FOREIGN KEY (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) REFERENCES TBL_GRUPO (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) ON DELETE RESTRICT,
    CONSTRAINT PK_TBL_INSCRIPCION    PRIMARY KEY (PERF_ID, CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_COORDINADOR
-- NOTA SCRUM-166: PERF_ID es NOT NULL pero NO tiene FK hacia TBL_PERFIL.
--   Inconsistencia preexistente en el DDL de Oracle -- no introducida
--   por esta migracion. Pendiente de decision de diseno.
-- --------------------------------------------------------
CREATE TABLE TBL_COORDINADOR (
    META_ELIMINADO INT         NOT NULL,
    PERF_ID        VARCHAR(50) NOT NULL,
    CONSTRAINT PK_TBL_COORDINADOR PRIMARY KEY (PERF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_BANDEJA_ENTRADA
-- --------------------------------------------------------
CREATE TABLE TBL_BANDEJA_ENTRADA (
    META_ELIMINADO INT         NOT NULL,
    PERF_ID        VARCHAR(50) NOT NULL,
    CONSTRAINT PK_TBL_BANDEJA_ENTRADA PRIMARY KEY (PERF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_BANDEJA_SALIDA
-- --------------------------------------------------------
CREATE TABLE TBL_BANDEJA_SALIDA (
    META_ELIMINADO INT         NOT NULL,
    PERF_ID        VARCHAR(50),
    CONSTRAINT PK_TBL_BANDEJA_SALIDA PRIMARY KEY (PERF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_NOTIFICACION
-- NTF_FECHAENVIO: sin entidad Java; DATETIME conservativo (Oracle DATE almacena hora)
-- NTF_IDENTIFICADOR: sin secuencia en Oracle; BIGINT NOT NULL sin AUTO_INCREMENT
--   (estrategia de poblacion desconocida al no existir entidad Java)
-- SCRUM-166 #14: PERF_ID parte de PK -> SET NULL imposible -> RESTRICT
--   Una notificacion sin emisor pierde trazabilidad de auditoria.
-- --------------------------------------------------------
CREATE TABLE TBL_NOTIFICACION (
    META_ELIMINADO    INT           NOT NULL,
    NTF_IDENTIFICADOR BIGINT        NOT NULL,
    PERF_ID           VARCHAR(50)   NOT NULL,
    NTF_EMISOR        VARCHAR(50)   NOT NULL,
    NTF_ASUNTO        VARCHAR(150)  NOT NULL,
    NTF_DESCRIPCION   VARCHAR(1000) NOT NULL,
    NTF_FECHAENVIO    DATETIME      NOT NULL,
    NTF_DESTINATARIOS VARCHAR(1000) NOT NULL,
    CONSTRAINT PK_TBL_NOTIFICACION            PRIMARY KEY (NTF_IDENTIFICADOR, PERF_ID),
    CONSTRAINT FK_NOTIFICACION_BANDEJA_SALIDA FOREIGN KEY (PERF_ID) REFERENCES TBL_BANDEJA_SALIDA (PERF_ID) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_ALERTA
-- SCRUM-166 #15: todas las columnas de FK son PK -> SET NULL imposible -> CASCADE
--   Las alertas son mensajes del grupo. Si el grupo desaparece, sus alertas tambien.
-- --------------------------------------------------------
CREATE TABLE TBL_ALERTA (
    META_ELIMINADO    INT           NOT NULL,
    GRP_ANIO          BIGINT        NOT NULL,
    GRP_ITERABLE      BIGINT        NOT NULL,
    CUR_NOMBRE        VARCHAR(100)  NOT NULL,
    CAT_TITULO        VARCHAR(100)  NOT NULL,
    ALERT_ASUNTO      VARCHAR(100)  NOT NULL,
    ALERT_DESCRIPCION VARCHAR(1000) NOT NULL,
    CONSTRAINT FK_ALERTA_CURSO FOREIGN KEY (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) REFERENCES TBL_GRUPO (CAT_TITULO, CUR_NOMBRE, GRP_ANIO, GRP_ITERABLE) ON DELETE CASCADE,
    CONSTRAINT PK_TBL_ALERTA   PRIMARY KEY (GRP_ANIO, GRP_ITERABLE, CUR_NOMBRE, CAT_TITULO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------
-- TBL_ESCENARIO
-- ESC_ID: era SEQ_ESC_ID (START 1) -> AUTO_INCREMENT
-- --------------------------------------------------------
CREATE TABLE TBL_ESCENARIO (
    META_ELIMINADO   TINYINT      DEFAULT 0 NOT NULL,
    ESC_ID           BIGINT       NOT NULL AUTO_INCREMENT,
    ESC_NOMBRE       VARCHAR(100) NOT NULL,
    ESC_DESCRIPCION  VARCHAR(500),
    ESC_NUM_TRIBUNAS SMALLINT     DEFAULT 0 NOT NULL,
    ESC_DISPONIBLE   TINYINT      DEFAULT 1 NOT NULL,
    CONSTRAINT PK_TBL_ESCENARIO        PRIMARY KEY (ESC_ID),
    CONSTRAINT UQ_TBL_ESCENARIO_NOMBRE UNIQUE (ESC_NOMBRE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- DATOS INICIALES (SEED DATA)
-- ============================================================

INSERT INTO TBL_PERFIL (META_ELIMINADO, PERF_ID, PERF_NOMBRE, PERF_CORREO, PERF_IMAGEN, PERF_TIPOID, PERF_SEXO) VALUES
    (0, '3',          'SMERT EMJ',                       'edynsonjm@gmail.com',          NULL, 'CC', 'M'),
    (0, '1',          'EDYNSON MUNOZ JIMENEZ',           'edinsonmjim@unicauca.edu.co',  NULL, 'CC', 'M'),
    (0, '2',          'edynson munoz jimenez',           'edynsonmj@gmail.com',          NULL, 'CC', 'M'),
    (0, '1141315391', 'MIGUEL ANGEL DORADO QUINAYAS',    'migueladorado@unicauca.edu.co', NULL, 'CC', 'M'),
    (0, '1002958703', 'Yonathan Fernandez Guengue',      'tochavi@unicauca.edu.co',      NULL, 'CC', 'M'),
    (0, '1061786866', 'Luis Alexis Mendez Rojas',        'lamendez@unicauca.edu.co',     NULL, 'CC', 'M'),
    (0, '1061700114', 'Mitchelt Hernan Chilito Villota', 'mhchilito@unicauca.edu.co',    NULL, 'CC', 'M'),
    (0, '1061721857', 'JOHAN RICARDO MENDEZ CASTRO',     'johanmendez@unicauca.edu.co',  NULL, 'TI', 'M'),
    (0, '1061813673', 'Juan Sebastian Pisso',            'jpisso@unicauca.edu.co',       NULL, 'CC', 'M');

INSERT INTO TBL_ALUMNO (META_ELIMINADO, PERF_ID, ALM_CODIGO, ALM_TIPO, ALM_ESTADO) VALUES
    (0, '3',          NULL, 'Estudiante',     NULL),
    (0, '1',          NULL, 'Administrativo', NULL),
    (0, '2',          NULL, 'Docente',        NULL),
    (0, '1141315391', NULL, 'Estudiante',     NULL),
    (0, '1002958703', NULL, 'Estudiante',     NULL),
    (0, '1061786866', NULL, 'Estudiante',     NULL),
    (0, '1061700114', NULL, 'Estudiante',     NULL),
    (0, '1061721857', NULL, 'Estudiante',     NULL);

INSERT INTO TBL_CATEGORIA_CURSO (META_ELIMINADO, CAT_TITULO, CAT_DESCRIPCION, CAT_IMAGEN) VALUES
    (0, 'seleccionado ', 'Es un espacio para el entrenamiento y participacion en eventos deportivos competitivos de caracter universitario, realizados a nivel local, nacional e internacional, bien sea en la red ASCUN o invitaciones de otras universidades.', NULL),
    (0, 'Recreativo',    'El deporte recreativo es la modalidad definida como aquella practicada por placer y diversion, sin ninguna intencion de competir o superar a un adversario.', NULL),
    (0, 'Semillero',     'Este espacio tiene la facultad de ayudar a desarrollar destrezas fisicas, hacer ejercicios, socializar, divertirse, aprender a jugar formando parte de un grupo o equipo, aprender a jugar limpio y a mejorar su autoestima.', NULL),
    (1, 'prueba',        'Ririr8t', NULL),
    (1, 'prurba2',       'Rur7r',   NULL),
    (1, 'ururt',         'Prueba',  NULL);

INSERT INTO TBL_DEPORTE (META_ELIMINADO, DEPT_NOMBRE) VALUES
    (0, 'Futbol'),
    (0, 'Baloncesto'),
    (0, 'Tenis'),
    (0, 'Natacion'),
    (0, 'Ping pong'),
    (0, 'Voleibol'),
    (0, 'Atletismo');

INSERT INTO TBL_FACULTAD (META_ELIMINADO, FAC_NOMBRE) VALUES (0, 'fiet');

INSERT INTO TBL_CURSO (META_ELIMINADO, CUR_NOMBRE, DEPT_NOMBRE, CAT_TITULO, CUR_DESCRIPCION, CUR_IMAGEN) VALUES
    (0, 'futbol 1',             'Futbol',     'seleccionado ', 'Este curso es un programa competitivo que busca desarrollar las habilidades tecnicas, tacticas y fisicas de los jugadores para enfrentar a otras facultades e instituciones a nivel local y nacional.', NULL),
    (0, 'baloncesto',           'Baloncesto', 'seleccionado ', 'Este curso es ideal para hombres y mujeres que buscan competir a nivel superior en baloncesto. Se enfoca en la practica de tecnicas avanzadas y la mejora de la condicion fisica.', NULL),
    (0, 'natacion',              'Natacion',   'Semillero',     'Descr', NULL),
    (0, 'Baloncesto masculino', 'Baloncesto', 'Recreativo',    'El baloncesto recreativo es una modalidad del deporte del baloncesto que se enfoca en la diversion y el disfrute, mas que en la competencia. En este entorno, los jugadores pueden desarrollar habilidades tecnicas y tacticas, mientras que tambien se enfocan en la socializacion y la relacion con otros jugadores.', NULL),
    (0, 'baloncesto seleccionado', 'Baloncesto', 'seleccionado ', 'El curso de baloncesto seleccionado es un espacio dentro del programa de deporte competitivo de la Universidad del Cauca, disenado para atletas con un alto nivel de habilidad y dedicacion. En este entorno, los estudiantes seleccionados tienen la oportunidad de entrenar y competir en eventos deportivos universitarios de caracter local, nacional e internacional.', NULL),
    (0, 'ping pong',            'Ping pong',  'Recreativo',    'El ping pong recreativo es una modalidad del deporte del ping pong que se enfoca en la diversion y el disfrute, mas que en la competencia. En este entorno, los jugadores pueden desarrollar habilidades tecnicas y tacticas, mientras que tambien se enfocan en la socializacion y la relacion con otros jugadores.', NULL);

INSERT INTO TBL_INSTRUCTOR (META_ELIMINADO, PERF_ID) VALUES (0, '2'), (0, '1061813673');
-- NOTA: el INSERT original de PERF_ID '987445' se omite porque no tiene entrada en TBL_PERFIL;
-- agregar primero el perfil correspondiente antes de insertar el instructor.

INSERT INTO TBL_COORDINADOR (META_ELIMINADO, PERF_ID) VALUES (0, '1');

-- TBL_ESCENARIO: ESC_ID omitido, AUTO_INCREMENT asigna valores 1-10
INSERT INTO TBL_ESCENARIO (META_ELIMINADO, ESC_NOMBRE, ESC_DESCRIPCION, ESC_NUM_TRIBUNAS, ESC_DISPONIBLE) VALUES
    (0, 'Piscina Olimpica',                    'Natacion. Seccion de clavados fuera de servicio.',                              0, 1),
    (0, 'Pista Atletica',                      'Medidas reglamentarias.',                                                       1, 1),
    (0, 'Cancha Descubierta de Voleibol',      'Medidas reglamentarias.',                                                       1, 1),
    (0, 'Cancha Polifuncional Descubierta',    'Baloncesto, futbol de salon, voleibol.',                                        1, 1),
    (0, 'Coliseo Cubierto Universitario',      'Baloncesto, futbol de salon, futbol sala, voleibol, actividades culturales.',   3, 1),
    (0, 'Patinodromo',                         'Medidas reglamentarias.',                                                       0, 1),
    (0, 'Dojos Cubiertos',                     'Karate do, aikido, taekwondo.',                                                 0, 1),
    (0, 'Sala de Ajedrez',                     NULL,                                                                            0, 1),
    (0, 'Salon de Aerobicos y Baile Deportivo', NULL,                                                                           0, 1),
    (0, 'Sala de Ping Pong',                   NULL,                                                                            0, 1);
