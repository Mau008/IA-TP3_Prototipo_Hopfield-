public class PrototipoHopfield {

    private int[][] weights;
    private int size;

    public PrototipoHopfield(int size) {
        this.size = size;
        this.weights = new int[size][size];
    }

    // Entrenamiento usando Regla de Hebb
    public void train(int[] pattern) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (i != j) {
                    weights[i][j] += pattern[i] * pattern[j];
                }

            }
        }
    }

    // Recuperación del patrón
    public int[] recall(int[] pattern, int iterations) {

        int[] state = pattern.clone();

        for (int iter = 0; iter < iterations; iter++) {

            for (int i = 0; i < size; i++) {

                int sum = 0;

                for (int j = 0; j < size; j++) {
                    sum += weights[i][j] * state[j];
                }

                state[i] = (sum >= 0) ? 1 : -1;
            }
        }

        return state;
    }

    // Mostrar patrón como imagen ASCII
    public static void printPattern(int[] pattern, String title) {

        System.out.println("\n=================================");
        System.out.println(title);
        System.out.println("=================================");

        for (int i = 0; i < 100; i++) {

            System.out.print(pattern[i] == 1 ? "█" : " ");

            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
    }

    // Calcular centro de la figura
    public static void calculateCenter(int[] pattern) {

        double xSum = 0;
        double ySum = 0;
        int count = 0;

        for (int i = 0; i < 100; i++) {

            if (pattern[i] == 1) {

                int x = i % 10;
                int y = i / 10;

                xSum += x;
                ySum += y;
                count++;
            }
        }

        if (count > 0) {

            double centerX = xSum / count;
            double centerY = ySum / count;

            System.out.println("\n=================================");
            System.out.println("COORDENADAS DEL CENTRO DETECTADO");
            System.out.println("=================================");
            System.out.printf("X = %.2f%n", centerX);
            System.out.printf("Y = %.2f%n", centerY);
        }
    }

    public static void main(String[] args) {

        System.out.println("PROTOTIPO HOPFIELD");
        System.out.println("Reconocimiento de Imagenes con Ruido");
        System.out.println("Patron 10x10");

        int[] figuraReferencia = {

            -1,-1,-1, 1, 1, 1,-1,-1,-1,-1,
            -1,-1, 1, 1, 1, 1, 1,-1,-1,-1,
            -1, 1, 1, 1, 1, 1, 1, 1,-1,-1,
             1, 1, 1, 1, 1, 1, 1, 1, 1,-1,
             1, 1, 1, 1, 1, 1, 1, 1, 1,-1,
             1, 1, 1, 1, 1, 1, 1, 1, 1,-1,
             1, 1, 1, 1, 1, 1, 1, 1, 1,-1,
            -1, 1, 1, 1, 1, 1, 1, 1,-1,-1,
            -1,-1, 1, 1, 1, 1, 1,-1,-1,-1,
            -1,-1,-1, 1, 1, 1,-1,-1,-1,-1
        };

        PrototipoHopfield redHopfield = new PrototipoHopfield(100);

        redHopfield.train(figuraReferencia);

        int[] imagenConRuido = figuraReferencia.clone();

        imagenConRuido[12] *= -1;
        imagenConRuido[25] *= -1;
        imagenConRuido[47] *= -1;
        imagenConRuido[60] *= -1;
        imagenConRuido[75] *= -1;

        printPattern(
                figuraReferencia,
                "FIGURA ORIGINAL ALMACENADA"
        );

        printPattern(
                imagenConRuido,
                "IMAGEN CON RUIDO"
        );

        int[] imagenRecuperada =
                redHopfield.recall(imagenConRuido, 10);

        printPattern(
                imagenRecuperada,
                "IMAGEN RECONSTRUIDA POR LA RED DE HOPFIELD"
        );

        calculateCenter(imagenRecuperada);
    }
}
