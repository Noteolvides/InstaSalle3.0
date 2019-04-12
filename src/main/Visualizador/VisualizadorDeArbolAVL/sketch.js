var canvas;
var fileTree;
var xAct;
var yAct;
var level = 1;
var offsetY = 150;
var insertionTimes;



function preOrder(nodeAct, nuevo, anterior) {
    if (level != 1) {
        line(nuevo, level * (offsetY + 2), anterior, (level - 1) * (offsetY + 2));
    }
    circle(nuevo, level * offsetY, 30);
    textAlign(CENTER);
    textSize(12)
    text(nodeAct.data, nuevo, (level * offsetY) + 5);

    if (nodeAct.leftChild != undefined) {
        level++
        preOrder(nodeAct.leftChild, Math.abs((anterior - nuevo) / 2 - nuevo), nuevo);
        level--;
    }
    if (nodeAct.rightChild != undefined) {
        level++;
        preOrder(nodeAct.rightChild, Math.abs((anterior - nuevo) / 2 + nuevo), nuevo);
        level--;
    }

}



function setup() {
    canvas = createCanvas(windowWidth, 4000);

    background(51);
    fileTree = document.getElementById("tree").innerHTML;
    aux = document.getElementById("tree");
    aux.parentNode.removeChild(tree);


    insertionTimes = document.getElementById("insertioTime").innerHTML;
    aux = document.getElementById("insertioTime");
    aux.parentNode.removeChild(insertioTime);



    fileTree = fileTree.trim();
    rootNode = JSON.parse(fileTree);


    preOrder(rootNode, windowWidth / 2, windowWidth);
}

function draw() {

}