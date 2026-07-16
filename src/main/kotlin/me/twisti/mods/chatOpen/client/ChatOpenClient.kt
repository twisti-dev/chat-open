package me.twisti.mods.chatOpen.client

import com.mojang.brigadier.Command
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component

class ChatOpenClient : ClientModInitializer {

    override fun onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("keepchatopen").executes { ctx ->
                    val enabled = !KeepChatOpenState.enabled
                    KeepChatOpenState.enabled = enabled
                    ctx.source.sendFeedback(
                        Component.literal("KeepChatOpen: " + if (enabled) "ON" else "OFF")
                            .withStyle(if (enabled) ChatFormatting.GREEN else ChatFormatting.RED)
                    )
                    Command.SINGLE_SUCCESS
                }
            )
        }
    }
}
