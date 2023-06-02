(ns resaltador)

(def er #"(?xi)
    (-? \d+ \. \d* (?: e [+-]? \d+)? ) # Grupo 1: Flotante
    | (\d+ )                        # Grupo 2: Entero
    | \"(?: [^\n\"] | \\\")* \"     # Grupo 3: Caracteres
    | (true)                        # Grupo 4: Verdadero
    | (false)                       # Grupo 5: Falso
    | (null)                        # Grupo 6: Nulo
    | ( [{] )                       # Grupo 7: Llave que abre
    | ( [}] )                       # Grupo 8: Llave que cierra
    | ( [\[] )                      # Grupo 9: Corchete que abre
    | ( [\]] )                      # Grupo 10: Corchete que cierra
    | ([:])                         # Grupo 11: Dospuntos
    | ([,])                         # Grupo 12: Coma
    | ( \s )                        # Grupo 13: Espacio
    | ( .? )                         # Grupo 14: Caracter invalido
")
;;| ( \\ )                        # Grupo 15: Backslash

(defn tokenize
  [input]
  (map (fn [token]
         (cond
           (token 1) [:flotante (token 0)]
           (token 2) [:entero (token 0)]
           (token 3) [:caracter (token 0)]
           (token 4) [:verdadero (token 0)]
           (token 5) [:falso (token 0)]
           (token 6) [:nulo (token 0)]
           (token 7) [:llave-izq (token 0)]
           (token 8) [:llave-der (token 0)]
           (token 9) [:corchete-izq (token 0)]
           (token 10) [:corchete-der (token 0)]
           (token 11) [:dos-puntos (token 0)]
           (token 12) [:coma (token 0)]
           (token 14) [:error (token 0)]
           ;(token 15) [:backslash (token 0)]
           ))
       (remove (fn [v] (v 13))(re-seq er input))))

(defn tokenize-file
  [file-name]
  (tokenize (slurp file-name)))

(tokenize-file "mi_ejemplo.json")
