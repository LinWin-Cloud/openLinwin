import os

os.system('clear')

def main():
    print('All Local Document: ')
    print(' ==> Rules File')
    print("""
==============================================
 1. DoNotVisitContent               (input 1)
 2. ipBlack                         (input 2)
 3. Security                        (input 3)
 4. URL_Return                      (input 4)

 [!] Enter 'exit' to qiut the console
==============================================""")
    options = input("Chose The Document >>> ")
    if options == "1":
        os.system('clear&&cat ../rules/documents/DoNotVisitContent.txt')
        main()
        return True
    if options == "2":
        os.system('clear&&cat ../rules/documents/ipBlack.txt')
        main()
        return True
    if options == "3":
        os.system('clear&&cat ../rules/documents/Security.txt')
        main()
        return True
    if options == "4":
        os.system('clear&&cat ../rules/documents/URL_Return.txt')
    if options == "exit":            
        exit()
    else:
        os.system('clear')
        main()
        return False

main()
