package org.crud.basico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.EventQueue;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import org.crud.basico.estudante.Estudante;
import org.crud.basico.estudante.GerenciarEstudante;

public class Main implements Callable<Void> {

    public static final String NOME_ARQUIVO_PERSISTENCE = "crud";
    public static final int ADICIONAR_ESTUDANTE = 1;
    public static final int LISTAR_ESTUDANTES = 2;
    public static final int PESQUISAR_POR_ID = 3;
    public static final int PESQUISAR_POR_NOME = 4;
    public static final int ATUALIZAR_ESTUDANTE = 5;
    public static final int EXCLUIR_ESTUDANTE = 6;
    public static final int SAIR = 7;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final GerenciarEstudante gerenciarEstudante;
    private final Scanner scanner;

    public static void main(String[] args) {
        new Main().call();
    }

    public Main() {
        entityManagerFactory = Persistence.createEntityManagerFactory(NOME_ARQUIVO_PERSISTENCE);
        entityManager = entityManagerFactory.createEntityManager();
        gerenciarEstudante = new GerenciarEstudante(entityManager);
        scanner = new Scanner(System.in);
    }

    private void execute() {
        while (true) {
            mostrarMenu();
            System.out.print("Escolha uma opção: \n> ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case ADICIONAR_ESTUDANTE -> adicionarEstudante();

                case LISTAR_ESTUDANTES -> listarEstudantes();

                case PESQUISAR_POR_ID -> pesquisarPorId();

                case PESQUISAR_POR_NOME -> pesquisarPorNome();

                case ATUALIZAR_ESTUDANTE -> atualizarEstudante();

                case EXCLUIR_ESTUDANTE -> excluirEstudante();

                case SAIR -> {
                    return;
                }

                default -> System.out.println("Opção inválida, por favor tente novamente...");
            }

            System.out.println();
        }
    }

    private void adicionarEstudante() {
        System.out.print("Digite o nome do estudante: \n> ");
        String nome = scanner.nextLine();

        System.out.print("Digite o email do estudante: \n> ");
        String email = scanner.nextLine();

        Estudante estudante = new Estudante();
        estudante.setNome(nome);
        estudante.setEmail(email);
        gerenciarEstudante.create(estudante);

        System.out.println("Estudante cadastrado com sucesso.");
    }

    private void listarEstudantes() {
        List<Estudante> estudantes = gerenciarEstudante.list();

        if (estudantes.isEmpty()) {
            System.out.println("Não há estudantes cadastrados.");
            return;
        }

        for (Estudante estudante : estudantes) {
            System.out.printf("ID: %d%n", estudante.getId());
            System.out.printf("Nome: %s%n", estudante.getNome());
            System.out.printf("E-Mail: %s%n", estudante.getEmail());
        }
    }

    private void pesquisarPorId() {
        System.out.print("Digite o ID do estudante a ser pesquisado: \n> ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Estudante estudante = gerenciarEstudante.findById(id);
        if (estudante == null) {
            System.out.println("O estudante solicitado não foi encontrado.");
            return;
        }

        System.out.printf("ID: %d%n", estudante.getId());
        System.out.printf("Nome: %s%n", estudante.getNome());
        System.out.printf("E-Mail: %s%n", estudante.getEmail());
    }

    private void pesquisarPorNome() {
        System.out.print("Digite o nome a ser pesquisado: \n> ");
        String nome = scanner.nextLine();

        List<Estudante> estudantes = gerenciarEstudante.searchByName(nome);

        if (estudantes.isEmpty()) {
            System.out.println("Não foi encontrado nenhum estudante com esse nome.");
            return;
        }

        for (Estudante estudante : estudantes) {
            System.out.printf("ID: %d%n", estudante.getId());
            System.out.printf("Nome: %s%n", estudante.getNome());
            System.out.printf("E-Mail: %s%n", estudante.getEmail());
        }
    }

    private void atualizarEstudante() {
        System.out.print("Digite o ID do estudante a ser atualizado: \n> ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Estudante estudante = gerenciarEstudante.findById(id);
        if (estudante == null) {
            System.out.println("O estudante solicitado não foi encontrado.");
            return;
        }

        System.out.print("Digite o novo nome do estudante: \n> ");
        String nome = scanner.nextLine();

        System.out.print("Digite o novo email do estudante: \n> ");
        String email = scanner.nextLine();

        estudante.setNome(nome);
        estudante.setEmail(email);

        gerenciarEstudante.update(estudante);
        System.out.println("Estudante atualizado com sucesso.");
    }

    private void excluirEstudante() {
        System.out.print("Digite o ID do estudante a ser excluído: \n> ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Estudante estudante = gerenciarEstudante.findById(id);
        if (estudante == null) {
            System.out.println("O estudante solicitado não foi encontrado.");
            return;
        }

        gerenciarEstudante.delete(estudante);
        System.out.println("Estudante excluído com sucesso.");
    }

    private void mostrarMenu() {
        System.out.printf("[ %d ] Adicionar estudante%n", ADICIONAR_ESTUDANTE);
        System.out.printf("[ %d ] Listar estudantes%n", LISTAR_ESTUDANTES);
        System.out.printf("[ %d ] Pesquisar por ID%n", PESQUISAR_POR_ID);
        System.out.printf("[ %d ] Pesquisar por nome%n", PESQUISAR_POR_NOME);
        System.out.printf("[ %d ] Atualizar estudante%n", ATUALIZAR_ESTUDANTE);
        System.out.printf("[ %d ] Excluir estudante%n", EXCLUIR_ESTUDANTE);
        System.out.printf("[ %d ] Sair%n", SAIR);
    }

    @Override
    public Void call() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            entityManagerFactory.close();
            entityManager.close();
        }));

        EventQueue.invokeLater(this::execute);

        return null;
    }
}
