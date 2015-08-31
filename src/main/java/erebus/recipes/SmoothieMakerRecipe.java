package erebus.recipes;

import erebus.core.helper.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmoothieMakerRecipe {
	private static final List<SmoothieMakerRecipe> recipes = new ArrayList<SmoothieMakerRecipe>();

	public static void addRecipe(ItemStack output, FluidStack fluid, Object... input) {
		addRecipe(output, new FluidStack[] { fluid }, input);
	}

	public static void addRecipe(ItemStack output, FluidStack[] fluids, Object... input) {
		recipes.add(new SmoothieMakerRecipe(output, fluids, input));
	}

	public static void addRecipe(ItemStack output, Fluid fluid, Object... input) {
		addRecipe(output, new Fluid[] { fluid }, input);
	}

	public static void addRecipe(ItemStack output, Fluid[] fluids, Object... input) {
		FluidStack[] stacks = new FluidStack[fluids.length];
		for (int i = 0; i < stacks.length; i++)
			stacks[i] = new FluidStack(fluids[i], FluidContainerRegistry.BUCKET_VOLUME);
		addRecipe(output, stacks, input);
	}

	public static ItemStack getOutput(IFluidTank tank0, IFluidTank tank1, IFluidTank tank2, IFluidTank tank3, ItemStack... input) {
		SmoothieMakerRecipe recipe = getRecipe(tank0, tank1, tank2, tank3, input);
		return recipe != null ? recipe.getOutput() : null;
	}

	public static SmoothieMakerRecipe getRecipe(IFluidTank tank0, IFluidTank tank1, IFluidTank tank2, IFluidTank tank3, ItemStack... input) {
		for (SmoothieMakerRecipe recipe : recipes)
			if (recipe.matches(tank0, tank1, tank2, tank3, input))
				return recipe;

		return null;
	}

	public static List<SmoothieMakerRecipe> getRecipeList() {
		return Collections.unmodifiableList(recipes);
	}

	private final ItemStack output;
	private final FluidStack[] fluids;
	private final Object[] input;

	private SmoothieMakerRecipe(ItemStack output, FluidStack[] fluids, Object... input) {
		this.output = ItemStack.copyItemStack(output);
		this.fluids = fluids;
		this.input = new Object[input.length];

		if (input.length > 4)
			throw new IllegalArgumentException("Input must be 1 to 4.");
		if (fluids.length > 4)
			throw new IllegalArgumentException("Fluids must be 1 to 4.");

		for (int c = 0; c < input.length; c++)
			if (input[c] instanceof ItemStack)
				this.input[c] = ItemStack.copyItemStack((ItemStack) input[c]);
			else if (input[c] instanceof String)
				this.input[c] = OreDictionary.getOres((String) input[c]);
			else
				throw new IllegalArgumentException("Input must be an ItemStack or an OreDictionary name");
	}

	public Object[] getInputs() {
		return input;
	}

	public ItemStack getOutput() {
		return ItemStack.copyItemStack(output);
	}

	public boolean matches(IFluidTank tank0, IFluidTank tank1, IFluidTank tank2, IFluidTank tank3, ItemStack... stacks) {
		label: for (Object input : this.input) {
			for (int i = 0; i < stacks.length; i++)
				if (stacks[i] != null)
					if (areStacksTheSame(input, stacks[i])) {
						stacks[i] = null;
						continue label;
					}

			return false;
		}

		for (FluidStack fluid : fluids)
			if (tank0.getFluidAmount() >= fluid.amount && tank0.getFluid().isFluidEqual(fluid))
				continue;
			else if (tank1.getFluidAmount() >= fluid.amount && tank1.getFluid().isFluidEqual(fluid))
				continue;
			else if (tank2.getFluidAmount() >= fluid.amount && tank2.getFluid().isFluidEqual(fluid))
				continue;
			else if (tank3.getFluidAmount() >= fluid.amount && tank3.getFluid().isFluidEqual(fluid))
				continue;
			else
				return false;

		return true;
	}

	public boolean isPartOfInput(ItemStack ingredient) {
		for (Object i : input)
			if (areStacksTheSame(i, ingredient))
				return true;

		return false;
	}

	public FluidStack[] getFluids() {
		FluidStack[] fluids = new FluidStack[this.fluids.length];
		for (int i = 0; i < fluids.length; i++)
			fluids[i] = this.fluids[i].copy();
		return fluids;
	}

	@SuppressWarnings("unchecked")
	public boolean areStacksTheSame(Object obj, ItemStack target) {
		if (obj instanceof ItemStack)
			return Utils.areStacksTheSame((ItemStack) obj, target, false);
		else if (obj instanceof List) {
			List<ItemStack> list = (List<ItemStack>) obj;

			for (ItemStack stack : list)
				if (Utils.areStacksTheSame(stack, target, false))
					return true;
		}

		return false;
	}
}
