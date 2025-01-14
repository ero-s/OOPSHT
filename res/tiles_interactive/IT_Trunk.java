package tiles_interactive;

import main.Panel;

public class IT_Trunk extends interactiveTile
{
    Panel panel;

    public IT_Trunk(Panel panel, int col, int row)
    {
        super(panel, col, row);
        this.panel = panel;

        this.worldX = panel.tileSize * col;
        this.worldY = panel.tileSize * row;

        down1 = setup("res/tiles_interactive/trunk", panel.tileSize, panel.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
