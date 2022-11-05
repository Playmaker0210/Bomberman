package uet.oop.bomberman.pathFinding;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;

import java.util.ArrayList;

/**
 * thuat toan tim duong duoc cai tien tu DFS, them phan so sanh trong so duong di de tim duong di toi uu
 */
public class PathFinder {
    public Node[][] node;
    public ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    public Node startNode;
    public Node endNode;
    public Node currentNode;
    boolean isEnd = false;
    int step = 0;

    public PathFinder(NttGroup levelManage) {
        instantiateNode(levelManage);
    }

    /**
     * khoi tao
     */
    public void instantiateNode(NttGroup levelManage) {
        node = new Node[BombermanGame.WIDTH][BombermanGame.HEIGHT];
        int col = 1;
        int row = 1;

        while (col < BombermanGame.WIDTH-1 && row < BombermanGame.HEIGHT-1) {
            node[col][row] = new Node(col, row);
            if (levelManage.map[col][row] != ' ') {
                node[col][row].setSolid(true);
            }
            col++;
            if (col == BombermanGame.WIDTH-1) {
                col = 1;
                row++;
            }
        }
    }

    public void reset() {
        int col = 1;
        int row = 1;

        while (col < BombermanGame.WIDTH-1 && row < BombermanGame.HEIGHT-1) {
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            node[col][row].setSolid(false);
            col++;
            if (col == BombermanGame.WIDTH-1) {
                col = 1;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        isEnd = false;
        step = 0;
    }

    /**
     * dat ra toa do diem den va toa do dich den
     */
    public void setNode(int startCol, int startRow, int endCol, int endRow) {
        // lam moi
        openList.clear();
        pathList.clear();
        isEnd = false;
        step = 0;
        int col = 1;
        int row = 1;
        while (col < BombermanGame.WIDTH-1 && row < BombermanGame.HEIGHT-1) {
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            col++;
            if (col == BombermanGame.WIDTH-1) {
                col = 1;
                row++;
            }
        }

        startNode = node[startCol][startRow];
        currentNode = startNode;
        endNode = node[endCol][endRow];
        openList.add(currentNode);

        col = 1;
        row = 1;
        while (col < BombermanGame.WIDTH-1 && row < BombermanGame.HEIGHT-1) {
            getCost(node[col][row]);
            col++;
            if (col == BombermanGame.WIDTH-1) {
                col = 1;
                row++;
            }
        }
    }

    /**
     * lay trong so
     */
    public void getCost(Node node) {
        // gCost la vi tri diem dang xet den diem xuat phat
        int distX = Math.abs(node.col - startNode.col);
        int distY = Math.abs(node.row - startNode.row);
        node.setgCost(distY + distX);

        //hCost la vi tri diem dang xet den dich
        distX = Math.abs(node.col - endNode.col);
        distY = Math.abs(node.row - endNode.row);
        node.sethCost(distX + distY);

        //fCost la tong quang duong tu diem xuat phat den dich neu di qua diem dang xet
        node.setfCost(node.gethCost() + node.getgCost());
    }

    /**
     * tim kiem cac diem co the den va tao ra duong di tot nhat
     */
    public boolean search() {
        while (!isEnd && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setChecked(true);
            openList.remove(currentNode);

            // kiem tra node o tren
            if (row - 1 >= 1) {
                openNode(node[col][row-1]);
            }

            // kiem tra node o ben trai
            if (col - 1 >= 1) {
                openNode(node[col-1][row]);
            }

            // kiem tra node o duoi
            if (row + 1 < BombermanGame.HEIGHT - 1) {
                openNode(node[col][row+1]);
            }

            if (col + 1 < BombermanGame.WIDTH - 1) {
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeCost = 999;

            // kiem tra phuong an tot nhat cho doi tuong theo cac tieu chi fCost, gCost
            for (int i = 0; i < openList.size(); i++) {
                if(openList.get(i).getfCost() < bestNodeCost) {
                    bestNodeIndex = i;
                    bestNodeCost = openList.get(i).getfCost();
                }
                if(openList.get(i).getfCost() == bestNodeCost) {
                    if(openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.size() == 0) {
                break;
            }
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == endNode) {
                isEnd = true;
                pathTrace();
            }
            step++;
        }
        return isEnd;
    }

    public void openNode(Node node) {
        if (!node.isOpen() && !node.isChecked() && !node.isSolid()) {
            node.setOpen(true);
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * truy van lai duong di de cho cac Entity co the su dung
     */
    public void pathTrace() {
        Node current = endNode;
        while (current != startNode) {
            pathList.add(current);
            current = current.parent;
        }
    }
}
