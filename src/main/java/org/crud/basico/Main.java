package org.crud.basico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.EventQueue;
import java.util.Scanner;
import java.util.concurrent.Callable;
import org.crud.basico.estudante.Estudante;
import org.crud.basico.estudante.GerenciarEstudante;

public class Main implements Callable<Void> {

    public static final String NOME_ARQUIVO_PERSISTENCE = "crud";
    public static final int ADICIONAR_ESTUDANTE = 1;
    public static final int LISTAR_ESTUDANTES = 2;
    public static final int SAIR = 3;
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
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case ADICIONAR_ESTUDANTE -> adicionarEstudante();

                case SAIR -> {
                    return;
                }

                default -> System.out.println("Opção inválida, por favor tente novamente...");
            }
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
    }

    private void mostrarMenu() {
        System.out.printf("[ %d ] Adicionar estudante%n", ADICIONAR_ESTUDANTE);
        System.out.printf("[ %d ] Listar estudantes%n", LISTAR_ESTUDANTES);
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
