#!/usr/bin/env python3

import subprocess


def formatar_arquivos():
    subprocess.run(['mvn', 'spotless:apply'])


comando_git = 'git diff --staged --name-only'
resultado_comando = subprocess.check_output(comando_git, shell=True)
resultado_comando = resultado_comando.decode('utf-8')
arquivos_modificados = resultado_comando.split('\n')
arquivos_modificados.pop()

if any('java' in arquivo for arquivo in arquivos_modificados):
    formatar_arquivos()

for arquivo in arquivos_modificados:
    subprocess.run(['git', 'add', arquivo])
