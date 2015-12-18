package mytest.jiang.wei.ipc.bindpool;

interface ISecurityCenter {
    String encrypt(String content);

    String decrypt(String password);
}