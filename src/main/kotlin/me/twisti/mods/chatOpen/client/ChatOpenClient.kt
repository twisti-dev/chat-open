package me.twisti.mods.chatOpen.client

import com.mojang.brigadier.Command
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.network.chat.Component

class ChatOpenClient : ClientModInitializer {

    override fun onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, context ->
            dispatcher.register(
                literal("keepchatopen")
                    .executes { ctx ->
                        KeepChatOpenState.enabled = !KeepChatOpenState.enabled
                        ctx.source.sendFeedback(
                            Component.literal("KeepChatOpen: " + (if (KeepChatOpenState.enabled) "ON" else "OFF"))
                        )
                        Command.SINGLE_SUCCESS
                    }
            )
        }
    }
}
