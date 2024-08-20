#include <stdio.h>

int main() {
    FILE *file = fopen("num.txt", "w");
    
    if (file == NULL) {
        perror("Error opening file");
        return 1;
    }

    // Buffer to store all port numbers in one go
    char buffer[100000 * 6]; // 100000 numbers, each up to 5 digits + newline
    char *ptr = buffer;

    for (int i = 1; i <= 100000; i++) {
        ptr += sprintf(ptr, "%d\n", i);
    }

    // Write the entire buffer to the file in one go
    fwrite(buffer, ptr - buffer, 1, file);

    fclose(file);

    printf("File 'num.txt' has been generated with port numbers from 1 to 100000.\n");
    
    return 0;
}

