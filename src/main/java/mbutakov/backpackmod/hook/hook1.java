package mbutakov.backpackmod.hook;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ua.agravaine.hooklib.asm.Hook;
import ua.agravaine.hooklib.asm.ReturnCondition;

public class hook1 {

	@Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
	public static void onEntityCollidedWithBlock(BlockLeaves that, World world, int x, int y, int z, Entity entity) {
	}

	@Hook(returnCondition = ReturnCondition.ON_TRUE, returnAnotherMethod = "getNullL")
	public static boolean getCollisionBoundingBoxFromPool(Block block, World world, int x, int y, int z) {
		if (block instanceof BlockLeavesBase)
			return true;
		if (block instanceof BlockTallGrass)
			return true;
		if (block instanceof BlockFlower)
			return true;
		if (block instanceof BlockBush)
			return true;
		return false;
	}
	
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void onEntityCollidedWithBlock(net.minecraft.block.BlockWeb block,World w, int x, int y, int z, Entity entity) {

	}

	
	public static AxisAlignedBB getNullL(Block block, World world, int n, int n2, int n3) {
		return null;
	}

	
	@Hook
	public static boolean renderStandardBlock(RenderBlocks renderBlocks, Block block, int n, int n2, int n3) {
		if (!(block instanceof BlockLeavesBase))
			return true;
		return false;
	}

	@Hook(returnCondition = ReturnCondition.ON_TRUE, returnAnotherMethod = "getFalse")
	public static boolean shouldSideBeRendered(Block block, IBlockAccess iBlockAccess, int n, int n2, int n3, int n4) {
		return iBlockAccess.getBlock(n, n2, n3) instanceof BlockLeavesBase;
	}

	public static boolean getFalse(Block block, IBlockAccess iBlockAccess, int n, int n2, int n3, int n4) {
		return true;
	}

}
