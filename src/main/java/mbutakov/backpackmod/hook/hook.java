package mbutakov.backpackmod.hook;

import ua.agravaine.hooklib.minecraft.HookLoader;
import ua.agravaine.hooklib.minecraft.PrimaryClassTransformer;

public class hook extends HookLoader {

	 // включает саму HookLib'у. Делать это можно только в одном из HookLoader'ов. При желании, можно включить gloomyfolken.hooklib.minecraft.HookLibPlugin и не указывать здесь это вовсе.
	 @Override
	 public String[] getASMTransformerClass() {
	     return new String[]{PrimaryClassTransformer.class.getName()};
	 }

	    @Override
	    public void registerHooks() {
	        //регистрируем класс, где есть методы с аннотацией @Hook
	        registerHookContainer("ru.hcs2k13.existence.hook.hook1");
	    }
	}