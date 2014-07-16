package erebus.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ContainerAntInventory extends Container {

	public int numRows = 1;

	public ContainerAntInventory(InventoryPlayer playerInventory, IInventory entityInventory) {
		int i = (numRows - 4) * 18;
		int j;
		int k;

		for (j = 0; j < numRows; ++j)
			for (k = 0; k < 3; ++k)
				addSlotToContainer(new Slot(entityInventory, k + j * 9, 26 + k * 54, 18 + j * 18));

		for (j = 0; j < 3; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));

		for (j = 0; j < 9; ++j)
			addSlotToContainer(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack is = null;
		Slot slot = (Slot) inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack()) {
			ItemStack is1 = slot.getStack();
			is = is1.copy();

			if (slotNumber < numRows * 9) {
				if (!mergeItemStack(is1, numRows * 9, inventorySlots.size(), true))
					return null;
			}
			else if (is1.getItem() == Items.shears || is1.getItem() == Items.bucket || is1.getItem() instanceof ItemHoe) {
				if (!mergeItemStack(is1, 0, 1, false))
					return null;
			}
			else if (!mergeItemStack(is1, 1, numRows * 3, false))
				return null;

			if (is1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return is;
	}
}