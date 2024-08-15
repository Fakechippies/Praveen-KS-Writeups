#include <stdio.h>

int main() {
    FILE *file = fopen("ports.txt", "w");
    
    if (file == NULL) {
        perror("Error opening file");
        return 1;
    }

    // Buffer to store all port numbers in one go
    char buffer[65536 * 6]; // 65536 numbers, each up to 5 digits + newline
    char *ptr = buffer;

    for (int i = 1; i <= 65536; i++) {
        ptr += sprintf(ptr, "%d\n", i);
    }

    // Write the entire buffer to the file in one go
    fwrite(buffer, ptr - buffer, 1, file);

    fclose(file);

    printf("File 'ports.txt' has been generated with port numbers from 1 to 65536.\n");
    
    return 0;
}

