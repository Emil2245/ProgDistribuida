/**
 * Base URL para llamadas a la API.
 *
 * - En Docker/Traefik: usa URLs relativas (misma origin) -> "".
 * - En Dev local: puedes setear VITE_API_BASE_URL, por ejemplo "http://localhost:8080".
 */
export const API_BASE_URL = (import.meta as any).env?.VITE_API_BASE_URL ?? "";

export function apiUrl(path: string) {
  if (!path.startsWith("/")) return `${API_BASE_URL}/${path}`;
  return `${API_BASE_URL}${path}`;
}


