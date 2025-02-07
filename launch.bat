@echo off
:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: SCRIPT CST8221 - Winter 2025
:: ---------------------------------------------------------------------
:: Begin of Script (Labs - W25)
:: ---------------------------------------------------------------------
CLS
:: LOCAL VARIABLES ....................................................
SET SRCDIR=src
SET BINDIR=bin
SET BINERR=javac.err
SET JARNAME=launch.jar
SET JAROUT=jar.out
SET JARERR=jar.err
SET DOCDIR=doc
SET DOCERR=javadoc.err
SET MAINCLASSSRC=src/Main.java
SET MAINCLASSBIN=Main

ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "@                                                                   @"
ECHO "@                   #       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@                  ##       @  A L G O N Q U I N  C O L L E G E  @  @"
ECHO "@                ##  #      @    JAVA APPLICATION PROGRAMMING    @  @"
ECHO "@             ###    ##     @     W I N T E R  -  2 0 2 5        @  @"
ECHO "@          ###    ##        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@        ###    ##                                                  @"
ECHO "@        ##    ###                 ###                              @"
ECHO "@         ##    ###                ###                              @"
ECHO "@           ##    ##               ###   #####  ##     ##  #####    @"
ECHO "@         (     (      ((((()      ###       ## ###   ###      ##   @"
ECHO "@     ((((     ((((((((     ()     ###   ######  ###  ##   ######   @"
ECHO "@        ((                ()      ###  ##   ##   ## ##   ##   ##   @"
ECHO "@         ((((((((((( ((()         ###   ######    ###     ######   @"
ECHO "@         ((         ((           ###                               @"
ECHO "@          (((((((((((                                              @"
ECHO "@   (((                      ((                                     @"
ECHO "@    ((((((((((((((((((((() ))                                      @"
ECHO "@         ((((((((((((((((()                                        @"
ECHO "@                                                                   @"
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"


ECHO "1. Compiling ......................"
javac -Xlint -d %BINDIR% %SRCDIR%\*.java 2> %BINERR%

ECHO "2. Creating Jar ..................."
cd bin
jar cvfe %JARNAME% %MAINCLASSBIN% . > ../%JAROUT% 2> ../%JARERR%

ECHO "3. Creating Javadoc ..............."
cd ..
javadoc -d %DOCDIR% %SRCDIR%\*.java 2> %DOCERR%

ECHO "4. Running Jar ...................."
cd bin
start java -jar %JARNAME%
ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on
:: ---------------------------------------------------------------------
:: End of Script (Labs - F24)
:: ------------------------------------------------------------------