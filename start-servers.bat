@echo off
title Iniciando Consul y Traefik

echo ========================================
echo   INICIANDO SERVICIOS DE DESARROLLO
echo ========================================
echo.

REM Iniciar Consul en una nueva ventana
echo Iniciando Consul en modo desarrollo...
start "CONSUL Server (Dev Mode)" cmd /k "consul agent -dev"

echo Esperando 3 segundos para que Consul se inicialice...
timeout /t 3 /nobreak >nul
echo.

REM Iniciar Traefik en una nueva ventana
echo Iniciando Traefik con archivo de configuracion...
start "TRAEFIK Proxy" cmd /k ".\traefik.exe --configFile=traefik.yml"

echo.
echo ========================================
echo   SERVICIOS INICIADOS CORRECTAMENTE
echo ========================================
echo Consul: http://localhost:8500
echo Traefik: http://localhost:8888
echo.
echo Esta ventana se cerrara en 5 segundos...
timeout /t 5 /nobreak >nul